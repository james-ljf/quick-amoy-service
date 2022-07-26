package com.kuaipin.search.server.service.impl;

import com.kuaipin.common.entity.Page;
import com.kuaipin.common.entity.Response;
import com.kuaipin.common.entity.dto.Code;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.common.exception.BizBusinessException;
import com.kuaipin.common.util.ObjectUtil;
import com.kuaipin.common.util.RedisUtil;
import com.kuaipin.common.constants.ErrorEnum;
import com.kuaipin.search.server.constants.RecommendRuleConstants;
import com.kuaipin.search.server.constants.SearchConstants;
import com.kuaipin.search.server.constants.singleton.SingletonSuggest;
import com.kuaipin.search.server.entity.response.GoodsInfoVO;
import com.kuaipin.search.server.external.CoverBottomComponent;
import com.kuaipin.search.server.external.PitPositionComponent;
import com.kuaipin.search.server.external.RecommendComponent;
import com.kuaipin.search.server.external.SearchComponent;
import com.kuaipin.search.server.service.SearchService;
import com.kuaipin.search.server.util.analyzer.Keyword;
import com.kuaipin.search.server.util.analyzer.TFIDFAnalyzer;
import com.kuaipin.user.api.entity.SearchRecordDTO;
import com.kuaipin.user.api.service.RecordProcessService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.lionsoul.jcseg.ISegment;
import org.lionsoul.jcseg.dic.ADictionary;
import org.lionsoul.jcseg.dic.DictionaryFactory;
import org.lionsoul.jcseg.extractor.impl.TextRankKeywordsExtractor;
import org.lionsoul.jcseg.segmenter.SegmenterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author: ljf
 * @DateTime: 2022/3/22 14:49
 */
@Service
public class SearchServiceImpl implements SearchService {

    private final Logger log = LoggerFactory.getLogger(SearchServiceImpl.class);

    private final RecommendComponent recommendComponent;

    private final SearchComponent searchComponent;

    private final CoverBottomComponent coverBottomComponent;

    private final PitPositionComponent pitPositionComponent;
    @Autowired
    public SearchServiceImpl(RecommendComponent recommendComponent,
                             SearchComponent searchComponent,
                             CoverBottomComponent coverBottomComponent,
                             PitPositionComponent pitPositionComponent){
        this.recommendComponent = recommendComponent;
        this.searchComponent = searchComponent;
        this.coverBottomComponent = coverBottomComponent;
        this.pitPositionComponent = pitPositionComponent;
    }

    @DubboReference
    private RecordProcessService recordProcessService;

    private static final ThreadPoolExecutor execThreadPool = new ThreadPoolExecutor(40, 80,
            1L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(40),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    /**
     * 匹配商品名称高亮部分的正则表达式
     */
    private static final Pattern PATTERN = Pattern.compile("<b>(.*)</b>");

    /**
     * 默认推荐数量最低值
     */
    private static final int DEFAULT_SIZE = 48;

    /**
     * 判断在做搜索结果分页时不需要执行添加搜索记录
     */
    private static final String FIRST_SEARCH = "1";

    /**
     * 搜索发现的条件数量
     */
    private static final int DISCOVER_SIZE = 3;

    private static final Lock LOCK = new ReentrantLock();

    @Override
    public Response<Object> goodsRecommendPanel(Long uid) {
        // 从redis获取已推荐过的商品数据
        List<Object> objects = RedisUtil.getList(SearchConstants.RECOMMEND_KEY, 0, -1);
        // 泛型转换得到已经推荐过的商品集合
        List<GoodsInfoVO> alreadyList = ObjectUtil.objToList(objects, GoodsInfoVO.class);
        // 用户没有登录的情况
        if (uid == null) {
            // ①召回商家是旗舰店的商品，②根据系统近期搜索记录召回商品，③召回含有推荐关键词数组中关键词的商品
            Future<List<GoodsInfoVO>> hotGoodsFuture = execThreadPool.submit(() -> recommendComponent.flagShipGoods(alreadyList));
            Future<List<GoodsInfoVO>> recentGoodsFuture = execThreadPool.submit(() -> recommendComponent.recentSearchGoods(alreadyList));
            Future<List<GoodsInfoVO>> newTrendGoodsFuture = execThreadPool.submit(() -> recommendComponent.newTrendGoods(alreadyList));
            // 存放异步结果
            List<GoodsInfoVO> hotGoodsList;
            List<GoodsInfoVO> recentGoodsList;
            List<GoodsInfoVO> newTrendGoodsList;
            try {
                // 获取异步结果
                hotGoodsList = hotGoodsFuture.get();
                recentGoodsList = recentGoodsFuture.get();
                newTrendGoodsList = newTrendGoodsFuture.get();
            } catch (ExecutionException | InterruptedException e) {
                log.error("[4010.goodsRecommendPanel error] : {}", ErrorEnum.EXECUTE_ERROR.getMsg());
                // 中断线程
                Thread.currentThread().interrupt();
                return Response.fail(Code.RESULT_NULL);
            }
            // 存放召回结果的合并
            List<GoodsInfoVO> goodsInfoVOList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(hotGoodsList)) {
                goodsInfoVOList.addAll(hotGoodsList);
            }
            if (CollectionUtils.isNotEmpty(recentGoodsList)) {
                goodsInfoVOList.addAll(recentGoodsList);
            }
            if (CollectionUtils.isNotEmpty(newTrendGoodsList)) {
                goodsInfoVOList.addAll(newTrendGoodsList);
            }
            // 如果三路召回的总数不足推荐的默认最低值，则启用推荐兜底策略的召回
            if (goodsInfoVOList.size() < DEFAULT_SIZE) {
                int size = (int) RecommendRuleConstants.NOT_LOGIN_RECALL_NUMBER.getType();
                List<GoodsInfoVO> hotList = coverBottomComponent.hotBottoming(alreadyList, size);
                goodsInfoVOList.addAll(hotList);
            }
            // 对结果进行去重与排序
            List<GoodsInfoVO> result = goodsInfoVOList.stream()
                    .distinct()
                    .sorted(Comparator.comparingInt(GoodsInfoVO::getGoodsComment).reversed())
                    .collect(Collectors.toList());
            // 将本次推荐的结果放入redis的队列中，防止刷新推荐出现重复
            execThreadPool.execute(() -> pushResultToList(result));
            log.info("[2002.goodsRecommendPanel success] :  req = {}, res = {}", uid, result);
            return Response.success(result);
        }
        // 用户登录的情况（①获取用户近期的搜索记录，通过搜索记录关键词去召回同类商品；②获取用户近期的浏览记录，召回同类商品）
        List<Object> objectList = RedisUtil.getList(SearchConstants.LOGIN_RECOMMEND_KEY, 0, -1);
        // 泛型转换得到已经推荐过的商品集合
        List<GoodsInfoVO> loginGoodsList = ObjectUtil.objToList(objectList, GoodsInfoVO.class);
        // 召回
        Future<List<GoodsInfoVO>> searchRecordFuture = execThreadPool.submit(() -> recommendComponent.searchRecordRecommend(uid, loginGoodsList));
        Future<List<GoodsInfoVO>> browseRecordFuture = execThreadPool.submit(() -> recommendComponent.browseRecordRecommend(uid, loginGoodsList));
        List<GoodsInfoVO> searchList;
        List<GoodsInfoVO> browseList;
        try {
            // 获取异步结果
            searchList = searchRecordFuture.get();
            browseList = browseRecordFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            log.error("[4011.goodsRecommendPanel error] : {}", ErrorEnum.EXECUTE_ERROR.getMsg());
            // 中断线程
            Thread.currentThread().interrupt();
            return Response.fail(Code.RESULT_NULL);
        }
        List<GoodsInfoVO> goodsInfoVOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(searchList)) {
            goodsInfoVOList.addAll(searchList);
        }
        if (CollectionUtils.isNotEmpty(browseList)) {
            goodsInfoVOList.addAll(browseList);
        }
        // 如果召回的数量没有达到最低推荐列表数量，就进行一路兜底召回
        if (goodsInfoVOList.size() < DEFAULT_SIZE) {
            int size = (int) RecommendRuleConstants.NOT_LOGIN_RECALL_NUMBER.getType();
            List<GoodsInfoVO> hotList = coverBottomComponent.hotBottoming(loginGoodsList, size);
            goodsInfoVOList.addAll(hotList);
        }
        List<GoodsInfoVO> result = goodsInfoVOList.stream()
                .distinct()
                .collect(Collectors.toList());
        // 将本次推荐存入登录的推荐队列，防止刷新推荐出现重复
        execThreadPool.execute(() -> pushLoginResultToList(result));
        return Response.success(result);
    }

    @Override
    public Response<Object> searchAssociation(String keyword) {
        AnalyzingInfixSuggester suggester = SingletonSuggest.buildSuggester();
        // 存放搜索联想出来的推荐词文档
        List<Lookup.LookupResult> lookupResultList = null;
        List<String> recommendWords = new ArrayList<>();
        // 联想词结果数量
        int num = SearchConstants.ASSOCIATIONAL_NUM;
        try {
            lookupResultList = suggester.lookup(keyword, new HashSet<>(), num, true, true);
        } catch (IOException e) {
            log.error("[4010.searchAssociation error] : {}, req = {}", ErrorEnum.SEARCH_ERROR.getMsg(), keyword);
        }
        // 如果有联想词，配置关键词高亮
        if (CollectionUtils.isNotEmpty(lookupResultList)) {
            recommendWords.addAll(highKeyList(lookupResultList));
        }
        List<String> results = recommendWords.stream().distinct().collect(Collectors.toList());
        return Response.success(results);
    }

    @Override
    public Response<Object> searchCategoryPanel(String typeName, PageDTO pageDTO) {
        List<GoodsInfoVO> sTypeList = searchComponent.smallTypeGoodsPanel(typeName);
        List<GoodsInfoVO> bTypeList = searchComponent.bigTypeGoodsPanel(typeName);
        // 存放所有搜索结果
        List<GoodsInfoVO> goodsInfoVOList = new ArrayList<>();
        goodsInfoVOList.addAll(sTypeList);
        goodsInfoVOList.addAll(bTypeList);
        // 若本次品类有结果
        if (CollectionUtils.isNotEmpty(goodsInfoVOList)) {
            // 获取当前页商品
            Page<GoodsInfoVO> results = setGoodsInfoPage(goodsInfoVOList, pageDTO);
            return Response.success(results);
        }
        return Response.success(goodsInfoVOList);
    }

    @Override
    public Response<Object> searchKeywordPanel(String keyword, Long uId, PageDTO pageDTO, String type) {
        // 召回数：商品名、商家名、小品类名
        Future<List<GoodsInfoVO>> goodsNameFuture = execThreadPool.submit(() -> searchComponent.goodsNameRecall(keyword));
        Future<List<GoodsInfoVO>> businessNameFuture = execThreadPool.submit(() -> searchComponent.businessNameRecall(keyword));
        Future<List<GoodsInfoVO>> stNameBrandFuture = execThreadPool.submit(() -> searchComponent.sTypeNameOrBrandRecall(keyword));
        // 该召回防止结果过少，并且作为填坑的顺位替补
        Future<List<GoodsInfoVO>> analyzerFuture = execThreadPool.submit(() -> searchComponent.analyzerKeywordGoods(keyword));
        // 存放异步结果
        List<GoodsInfoVO> goodsNameList;
        List<GoodsInfoVO> businessNameList;
        List<GoodsInfoVO> sTypeNameList;
        List<GoodsInfoVO> analyzerList;
        try {
            // 获取异步结果
            goodsNameList = goodsNameFuture.get();
            businessNameList = businessNameFuture.get();
            sTypeNameList = stNameBrandFuture.get();
            analyzerList = analyzerFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            log.error("[4012.searchKeywordPanel error] : {}", ErrorEnum.EXECUTE_ERROR.getMsg());
            // 中断线程
            Thread.currentThread().interrupt();
            return Response.fail(Code.RESULT_NULL);
        }
        List<GoodsInfoVO> goodsInfoVOList;
        // 是否使用了分词的召回,true使用
        boolean isEnough = CollectionUtils.isNotEmpty(analyzerList);
        if (CollectionUtils.isNotEmpty(goodsNameList) || CollectionUtils.isNotEmpty(sTypeNameList)){
            // 将结果放入map集合用于填坑位
            Map<String, Iterator<GoodsInfoVO>> listMap = new ConcurrentHashMap<>(6);
            listMap.put(SearchConstants.G_NAME, goodsNameList.iterator());
            listMap.put(SearchConstants.S_NAME, sTypeNameList.iterator());
            goodsInfoVOList = pitPositionComponent.searchPitFilling(listMap, analyzerList);
        }else {
            // 若召回无结果，不填坑
            goodsInfoVOList = new ArrayList<>(analyzerList);
            goodsInfoVOList = goodsInfoVOList.stream().distinct().collect(Collectors.toList());
        }
        // 本次搜索是否有结果
        boolean isResult = false;
        // 存放物理分页的最终结果
        Page<GoodsInfoVO> results = new Page<>();
        if (CollectionUtils.isNotEmpty(goodsInfoVOList)) {
            // 获取当前页商品
            results = setGoodsInfoPage(goodsInfoVOList, pageDTO);
            isResult = true;
        }
        // 获取相关商家
        GoodsInfoVO businessVO = null;
        if (CollectionUtils.isNotEmpty(businessNameList)) {
            businessVO = businessNameList.get(0);
        }
        // 添加搜索记录
        boolean finalIsResult = isResult;
        if (FIRST_SEARCH.equals(type)){
            execThreadPool.execute(() -> setSearchRecord(uId, keyword, finalIsResult));
        }
        // 结果扩展项
        Map<String, Object> map = new HashMap<>(3);
        map.put("business", businessVO);
        map.put("isEnough", isEnough);
        return Response.success(results, map);
    }

    @Override
    public Response<Object> searchDiscovery(Long uid) {
        // 获取用户搜索记录
        List<SearchRecordDTO> searchRecordDTOList = recordProcessService.latelySearchHistory(uid, DISCOVER_SIZE);
        // 取出搜索记录的关键词并去重
        List<String> keywords = searchRecordDTOList.stream().distinct()
                .map(SearchRecordDTO::getSearchKeyword).collect(Collectors.toList());
        // 创建搜索联想工具实例
        AnalyzingInfixSuggester suggester = SingletonSuggest.buildSuggester();
        // 存放搜索联想出来的推荐词文档
        List<Lookup.LookupResult> lookupResultList = new ArrayList<>();
        try {
            for (String keyword : keywords) {
                lookupResultList.addAll(suggester.lookup(keyword, new HashSet<>(), DISCOVER_SIZE, true, true));
            }
        } catch (IOException e) {
            log.error("[4013.searchDiscovery error] : {}", ErrorEnum.SEARCH_ERROR.getMsg());
        }
        List<String> results = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(lookupResultList)) {
            // 推荐词分词处理
            results = analyzerKeyList(lookupResultList);
        }
        results = results.stream().distinct().collect(Collectors.toList());
        return Response.success(results);
    }

    @Override
    public Response<Object> searchGoods(String goodsNumber) {
        GoodsInfoVO resultVO = searchComponent.getGoodsInfoByNumber(goodsNumber);
        if (ObjectUtils.isEmpty(resultVO)){
            return Response.fail(Code.RESULT_NULL);
        }
        return Response.success(resultVO);
    }

    /**
     * 取出搜索联想数组的推荐词段，用于搜索发现
     *
     * @param lookupResultList 联想结果
     * @return 推荐词结果
     */
    public List<String> analyzerKeyList(List<Lookup.LookupResult> lookupResultList) {
        // 取出得分前3个
        int topN = 3;
        // 存放提取出来的关键词
        List<Keyword> keywordList = new ArrayList<>();
        for (Lookup.LookupResult result : lookupResultList) {
            String key = (String) result.key;
            // 以空格分割成字符数组
            String[] highKeyArray = key.split(" ");

            // 如果数组长度大于1，那么该推荐词是商品名,通过分词算法拿到分值高的关键词
            if (highKeyArray.length > 1) {
                TFIDFAnalyzer analyzer = new TFIDFAnalyzer();
                // 添加关键词
                keywordList.addAll(analyzer.analyze(key, topN));
            }
        }
        return keywordList.stream().map(Keyword::getName).collect(Collectors.toList());
    }


    /**
     * 取出搜索联想数组的高亮推荐词段
     * 此方法运用了分词器处理推荐词
     *
     * @param lookupResultList 联想结果
     * @param keyword          关键词
     * @return 推荐词结果
     * @deprecated 处理复杂度过高，没达到api响应时间标准
     */
    @Deprecated(since = "v1.1", forRemoval = false)
    public List<String> highKeyList(List<Lookup.LookupResult> lookupResultList, String keyword) throws IOException {
        // 存储高亮推荐词最终结果
        List<String> results = new ArrayList<>();
        // Jcseg分词器
        SegmenterConfig config = new SegmenterConfig(true);
        ADictionary aDictionary = DictionaryFactory.createDefaultDictionary(config);
        ISegment segment = ISegment.COMPLEX.factory.create(config, aDictionary);
        // 关键短语提取器
        TextRankKeywordsExtractor extractor = new TextRankKeywordsExtractor(segment);
        extractor.setKeywordsNum(15);
        for (Lookup.LookupResult result : lookupResultList) {
            String keyName = (String) result.key;
            StringBuilder keyValue = new StringBuilder();
            // 如果数组长度大于7，那么该推荐词是商品名
            if (keyName.length() > 7) {
                List<String> keyPhrases = extractor.getKeywords(new StringReader(keyName));
                boolean isBreak = false;
                for (String keyPhrase : keyPhrases) {
                    if (isBreak) {
                        keyValue.append(keyPhrase);
                        break;
                    }
                    if (keyPhrase.contains(keyword)) {
                        keyValue.append(keyPhrase);
                        isBreak = true;
                    }
                }
                results.add(keyValue.toString());
            } else {
                results.add(keyName);
            }
        }
        return results;
    }

    /**
     * 取出搜索联想数组的高亮推荐词段
     * 未使用分词器处理
     *
     * @param lookupResultList 联想结果
     * @return 推荐词结果
     */
    public List<String> highKeyList(List<Lookup.LookupResult> lookupResultList) {
        // 存储高亮推荐词最终结果
        List<String> results = new ArrayList<>();
        for (Lookup.LookupResult result : lookupResultList) {
            String highKey = (String) result.highlightKey;
            // 以空格分割成字符数组
            String[] highKeyArray = highKey.split(" ");
            // 如果数组长度大于1，那么该推荐词是商品名，则只取含有高亮标签<b>的那一个下标的词段
            if (highKeyArray.length > 1) {
                String key = null;
                for (String highStr : highKeyArray) {
                    Matcher matcher = PATTERN.matcher(highStr);
                    if (matcher.find()) {
                        key = highStr;
                        key = key.replace("<b>", "");
                        key = key.replace("</b>", "");
                        break;
                    }
                }
                results.add(key);
                continue;
            }
            // 获取高亮的key
            results.add((String) result.key);
        }
        return results;
    }

    /**
     * 对商品列表进行分页
     *
     * @param goodsInfoVOList 商品列表
     * @param pageDTO         分页实体
     * @return 当前页数据
     */
    public Page<GoodsInfoVO> setGoodsInfoPage(List<GoodsInfoVO> goodsInfoVOList, PageDTO pageDTO) {
        // 进行分页
        int pageNum = pageDTO.getPageNum();
        int pageSize = pageDTO.getPageSize();
        int start = (pageNum - 1) * pageSize;
        int end = pageSize + start;
        int length = goodsInfoVOList.size();
        if (end > length){
            end = length;
        }
        // 总数
        long total = goodsInfoVOList.size();
        if (goodsInfoVOList.size() >= SearchConstants.MIN_LIMIT){
            // 获取当前页的商品列表
            List<GoodsInfoVO> result = new ArrayList<>();
            for (int i = start; i < end; i++) {
                result.add(goodsInfoVOList.get(i));
            }

            return new Page<>(total, pageNum, pageSize, result);
        }
        return new Page<>(total, pageNum, pageSize, goodsInfoVOList);

    }

    /**
     * 将本次搜索记录添加到系统数据库
     *
     * @param uId     用户id
     * @param keyword 关键词
     */
    public void setSearchRecord(Long uId, String keyword, boolean isResult) {
        SearchRecordDTO searchRecordDTO = new SearchRecordDTO();
        searchRecordDTO.setSearchKeyword(keyword);
        searchRecordDTO.setUId(uId);
        searchRecordDTO.setIsResult(SearchConstants.NOT_RESULT);
        if (isResult) {
            searchRecordDTO.setIsResult(SearchConstants.HAS_RESULT);
        }
        int num;
        LOCK.lock();
        try{
            num = recordProcessService.increaseSearchRecord(searchRecordDTO);
        }catch (Exception e){
            throw new BizBusinessException(e.getMessage());
        }finally {
            LOCK.unlock();
        }
        if (num != 0){
            log.info("插入了{}条搜索记录,req = {}", num, searchRecordDTO);
        }
    }

    /**
     * 未登录的推荐防重复队列
     * 将本次推荐结果放入redis的队列中
     *
     * @param list 本次推荐结果
     */
    public void pushResultToList(List<GoodsInfoVO> list) {
        long time = SearchConstants.TIMER;
        for (GoodsInfoVO goodsInfoVO : list) {
            RedisUtil.setList(SearchConstants.RECOMMEND_KEY, goodsInfoVO, time);
        }
    }

    /**
     * 登录的推荐防重复队列
     * 将本次推荐结果放入redis的队列中
     *
     * @param list 本次推荐结果
     */
    public void pushLoginResultToList(List<GoodsInfoVO> list) {
        long time = SearchConstants.TIMER;
        for (GoodsInfoVO goodsInfoVO : list) {
            RedisUtil.setList(SearchConstants.LOGIN_RECOMMEND_KEY, goodsInfoVO, time);
        }
    }

}
