package com.kuaipin.search.server.external;

import com.kuaipin.search.server.util.BoolQueryBuilders;
import com.kuaipin.search.server.util.LuceneUtil;
import com.kuaipin.common.constants.ErrorEnum;
import com.kuaipin.search.server.constants.IndexConstants;
import com.kuaipin.search.server.constants.RecommendRuleConstants;
import com.kuaipin.search.server.constants.SearchConstants;
import com.kuaipin.search.server.convert.EntityCreation;
import com.kuaipin.search.server.entity.response.GoodsInfoVO;
import com.kuaipin.user.api.entity.BrowseRecordDTO;
import com.kuaipin.user.api.entity.SearchRecordDTO;
import com.kuaipin.user.api.service.RecordProcessService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 推荐列表业务
 * @Author: ljf
 * @DateTime: 2022/3/22 14:58
 */
@Component
public class RecommendComponent {

    private final Logger log = LoggerFactory.getLogger(RecommendComponent.class);

    private EntityCreation entityCreation;
    @Autowired
    private void setEntityCreation(EntityCreation entityCreation){
        this.entityCreation = entityCreation;
    }

    @DubboReference
    private RecordProcessService recordProcessService;

    /**
     * 情况：未登录
     * 根据商家是否是旗舰店召回商品
     * @param notExistList 缓存的已经推荐过的商品列表
     * @return  商品列表
     */
    public List<GoodsInfoVO> flagShipGoods(List<GoodsInfoVO> notExistList){
        IndexReader reader = LuceneUtil.buildIndexReader();
        IndexSearcher searcher = LuceneUtil.buildIndexSearcher(reader);
        // 条件:旗舰店的商家
        BoolQueryBuilders builders = new BoolQueryBuilders();
        builders.must(new TermQuery(new Term(IndexConstants.IS_FLAGSHIP, SearchConstants.IS_FLAGSHIP)));
        if (CollectionUtils.isNotEmpty(notExistList)){
            // 必要不存在条件
            builders.mustNotAll(notExistList);
        }
        Query query = builders.build();
        // 构造排序规则（商品评论降序和商品价格升序）
        Sort sort = new Sort(new SortField(IndexConstants.GOODS_COMMENT, SortField.Type.INT, false),
                new SortField(IndexConstants.GOODS_PRICE, SortField.Type.INT, true));
        int size = (Integer) RecommendRuleConstants.NOT_LOGIN_RECALL_NUMBER.getType();
        try{
            // 获取搜索结果
            TopDocs topDocs = searcher.search(query, size, sort);
            // 返回经过类转换的商品列表
            return entityCreation.docConvertVO(topDocs, searcher);
        }catch (IOException | ParseException e) {
            log.error("[4001.getFlagshipGoods error]: msg = {}", e.toString());
            return new ArrayList<>();
        }
    }

    /**
     * 情况：未登录
     * 召回有近期五条搜索记录关键词的商品
     * 条件：商品名称或商家名称必须有该关键词字段
     * 搜索记录为Null的情况下会进行兜底
     * @param notExistList 缓存的已经推荐过的商品列表
     * @return  商品列表
     */
    public List<GoodsInfoVO> recentSearchGoods(List<GoodsInfoVO> notExistList){
        // 获取系统近期五条搜索记录
        List<SearchRecordDTO> searchHistoryDTOList = recordProcessService.latelySearchHistory(null, SearchConstants.HISTORY_SIZE);
        if (CollectionUtils.isEmpty(searchHistoryDTOList)){
            // 系统无搜索记录，此路召回为NULL,返回空数组
            return new ArrayList<>();
        }
        // 如果有搜索记录，则获取搜索记录的关键词去召回
        IndexReader reader = LuceneUtil.buildIndexReader();
        IndexSearcher searcher = LuceneUtil.buildIndexSearcher(reader);
        BoolQueryBuilders builders = new BoolQueryBuilders();
        // 将搜索记录的关键词添加到搜索条件中(“或”条件)
        for (SearchRecordDTO searchRecordDTO : searchHistoryDTOList) {
            // 条件：商品的名称或者商品的品牌含有关键字
            builders.should(new FuzzyQuery(new Term(IndexConstants.GOODS_NAME, searchRecordDTO.getSearchKeyword()), 1));
            builders.should(new TermQuery(new Term(IndexConstants.GOODS_BRAND, searchRecordDTO.getSearchKeyword())));
        }
        if (CollectionUtils.isNotEmpty(notExistList)){
            // 必要不存在条件
            builders.mustNotAll(notExistList);
        }
        BooleanQuery query = builders.build();
        // 排序规则(按商品评论数降序排序)
        Sort sort = new Sort(new SortField(IndexConstants.GOODS_COMMENT, SortField.Type.INT, false),
                new SortField(IndexConstants.GOODS_PRICE, SortField.Type.INT, true));
        // 召回数量
        int number = (Integer) RecommendRuleConstants.NOT_LOGIN_RECALL_NUMBER.getType();
        try{
            // 获取搜索结果
            TopDocs topDocs = searcher.search(query, number, sort);
            return entityCreation.docConvertVO(topDocs, searcher);
        } catch (IOException | ParseException e) {
            log.error("[4002.recentSearchGoods] : msg = {}, exception= {}", ErrorEnum.SEARCH_ERROR.getMsg(), e.toString());
        }
        return new ArrayList<>();
    }

    /**
     * 情况：未登录
     * 召回含有推荐规则枚举类的关键词数组的商品
     * 条件：商品名字中含有该关键词
     * @param notExistList 缓存的已经推荐过的商品列表
     * @return  商品列表
     */
    public List<GoodsInfoVO> newTrendGoods(List<GoodsInfoVO> notExistList){
        IndexReader reader = LuceneUtil.buildIndexReader();
        IndexSearcher searcher = LuceneUtil.buildIndexSearcher(reader);
        // 召回条件存储
        BoolQueryBuilders builders = new BoolQueryBuilders();
        // 获取关键词数组
        String[] term = (String[]) RecommendRuleConstants.TERM_LIST.getType();
        for (String key : term) {
            builders.should(new FuzzyQuery(new Term(IndexConstants.GOODS_NAME, key)));
        }
        if (CollectionUtils.isNotEmpty(notExistList)){
            // 必要不存在条件
            builders.mustNotAll(notExistList);
        }
        // 构造条件
        Query query = builders.build();
        int size = (Integer) RecommendRuleConstants.NOT_LOGIN_RECALL_NUMBER.getType();
        try{
            // 获取搜索结果
            TopDocs topDocs =  searcher.search(query, size, Sort.RELEVANCE);
            return entityCreation.docConvertVO(topDocs, searcher);
        }catch (IOException | ParseException e) {
            log.error("[4006.newTrendGoods error]: msg = {}", e.toString());
            return new ArrayList<>();
        }
    }

    /**
     * 情况：已登录
     * 查询用户的近五条搜索记录，获取搜索的关键词做推荐的词组，用布尔查询去召回商品数据
     * @param uId 用户id
     * @param notExistList  缓存的已经推荐过的商品列表
     * @return  商品列表
     */
    public List<GoodsInfoVO> searchRecordRecommend(Long uId, List<GoodsInfoVO> notExistList){
        // 获取用户近期五条搜索记录
        List<SearchRecordDTO> searchHistoryDTOList = recordProcessService.latelySearchHistory(uId, SearchConstants.HISTORY_SIZE);
        if (CollectionUtils.isEmpty(searchHistoryDTOList)){
            return new ArrayList<>();
        }
        IndexReader reader = LuceneUtil.buildIndexReader();
        IndexSearcher searcher = LuceneUtil.buildIndexSearcher(reader);
        BoolQueryBuilders builders = new BoolQueryBuilders();
        for (SearchRecordDTO searchRecordDTO : searchHistoryDTOList) {
            // 添加条件
            builders.should(new FuzzyQuery(new Term(IndexConstants.GOODS_NAME, searchRecordDTO.getSearchKeyword())));
        }
        if (CollectionUtils.isNotEmpty(notExistList)){
            // 必要不存在条件
            builders.mustNotAll(notExistList);
        }
        Query query = builders.build();
        // 构造排序规则（商品评论降序和商品价格升序）
        Sort sort = new Sort(new SortField(IndexConstants.GOODS_COMMENT, SortField.Type.INT, false));
        int size = (int) RecommendRuleConstants.LOGIN_RECALL_NUMBER.getType();
        try{
            // 获取结果
            TopDocs topDocs = searcher.search(query, size, sort);
            return entityCreation.docConvertVO(topDocs, searcher);
        }catch (IOException | ParseException e){
            log.error("[4011.searchRecordRecommend error]: msg = {}", e.toString());
        }
        return new ArrayList<>();
    }

    /**
     * 情况：已登录
     * 查询用户的近期浏览记录，获取浏览记录里的小品类id数组，召回同品类的更多商品
     * @param uId   用户id
     * @param notExistList  缓存的已经推荐过的商品列表
     * @return  商品列表
     */
    public List<GoodsInfoVO> browseRecordRecommend(Long uId, List<GoodsInfoVO> notExistList){
        // 获取用户近期的浏览记录
        List<BrowseRecordDTO> browseRecordDTOList = recordProcessService.latelyBrowseRecord(uId, SearchConstants.HISTORY_SIZE);
        if (CollectionUtils.isEmpty(browseRecordDTOList)){
            return new ArrayList<>();
        }
        // 获取浏览记录的商品所属小品类id并去重
        Set<Long> sTypeIdList = browseRecordDTOList.stream().map(BrowseRecordDTO::getSTypeId).collect(Collectors.toSet());
        IndexReader reader = LuceneUtil.buildIndexReader();
        IndexSearcher searcher = LuceneUtil.buildIndexSearcher(reader);
        BoolQueryBuilders builders = new BoolQueryBuilders();
        for (Long sTypeId : sTypeIdList) {
            builders.should(new TermQuery(new Term(IndexConstants.S_TYPE_ID, String.valueOf(sTypeId))));
        }
        if (CollectionUtils.isNotEmpty(notExistList)){
            // 必要不存在条件
            builders.mustNotAll(notExistList);
        }
        Query query = builders.build();
        // 评论数降序
        Sort sort = new Sort(new SortField(IndexConstants.GOODS_COMMENT, SortField.Type.INT, false));
        int size = (int) RecommendRuleConstants.LOGIN_RECALL_NUMBER.getType();
        try{
            // 获取结果
            TopDocs topDocs = searcher.search(query, size, sort);
            return entityCreation.docConvertVO(topDocs, searcher);
        }catch (IOException | ParseException e){
            log.error("[4012.browseRecordRecommend error]: msg = {}", e.toString());
        }
        return new ArrayList<>();
    }

}
