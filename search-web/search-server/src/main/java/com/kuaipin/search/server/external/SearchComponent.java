package com.kuaipin.search.server.external;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.kuaipin.common.constants.ErrorEnum;
import com.kuaipin.search.server.constants.IndexConstants;
import com.kuaipin.search.server.constants.SearchConstants;
import com.kuaipin.search.server.convert.EntityCreation;
import com.kuaipin.search.server.entity.response.GoodsInfoVO;
import com.kuaipin.search.server.util.BoolQueryBuilders;
import com.kuaipin.search.server.util.LuceneUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 搜索业务
 * @Author: ljf
 * @DateTime: 2022/4/5 15:49
 */
@Component
public class SearchComponent {

    private final Logger log = LoggerFactory.getLogger(SearchComponent.class);

    private EntityCreation entityCreation;
    @Autowired
    private void setEntityCreation(EntityCreation entityCreation){
        this.entityCreation = entityCreation;
    }

    /**
     * 根据关键词模糊搜索商品名称
     * @param keyword   关键词
     * @return  商品列表
     */
    public List<GoodsInfoVO> goodsNameRecall(String keyword){
        IndexSearcher searcher = LuceneUtil.buildIndexSearcher();
        String key = IndexConstants.GOODS_NAME;
        // 商品名搜索
        Query query = new FuzzyQuery(new Term(key, keyword), 0);
        // 评论数降序
        Sort sort = new Sort(new SortField(IndexConstants.GOODS_COMMENT, SortField.Type.INT, false));
        try{
            TopDocs topDocs = searcher.search(query, SearchConstants.SEARCH_SIZE, sort);
            return entityCreation.docConvertVoHigh(topDocs, searcher,  key, keyword);
        }catch (IOException | ParseException | InvalidTokenOffsetsException e){
            log.error("[4201.productNameRecall error] : {}, msg = {}", ErrorEnum.SEARCH_ERROR.getMsg(), e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * 根据关键词模糊搜索商家名称
     * @param keyword   关键词
     * @return  商品列表
     */
    public List<GoodsInfoVO> businessNameRecall(String keyword){
        IndexSearcher searcher = LuceneUtil.buildIndexSearcher();
        // 商家名称模糊搜索
        Query query = new TermQuery(new Term(IndexConstants.BUSINESS_NAME, keyword));
        // 评论数降序
        Sort sort = new Sort(new SortField(IndexConstants.GOODS_COMMENT, SortField.Type.INT, false));
        try{
            TopDocs topDocs = searcher.search(query, 1, sort);
            return entityCreation.docConvertVO(topDocs, searcher);
        }catch (IOException | ParseException e){
            log.error("[4202.businessNameRecall error] : {}, msg = {}", ErrorEnum.SEARCH_ERROR.getMsg(), e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * 根据关键词搜索商品小品类 或 品牌
     * @param keyword   关键词
     * @return  商品列表
     */
    public List<GoodsInfoVO> sTypeNameOrBrandRecall(String keyword){
        IndexSearcher searcher = LuceneUtil.buildIndexSearcher();
        // 构造搜索条件
        BoolQueryBuilders builders = new BoolQueryBuilders();
        builders.should(new FuzzyQuery(new Term(IndexConstants.S_TYPE_NAME, keyword), 1));
        builders.should(new FuzzyQuery(new Term(IndexConstants.GOODS_BRAND, keyword), 1));
        Query query = builders.build();
        try{
            TopDocs topDocs = searcher.search(query, SearchConstants.SEARCH_SIZE);
            return entityCreation.docConvertVO(topDocs, searcher);
        }catch (IOException | ParseException e){
            log.error("[4203.sTypeNameRecall error] : {}, msg = {}", ErrorEnum.SEARCH_ERROR.getMsg(), e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * 关键词分词后按商品名称召回商品
     * @param keyword   关键词
     * @return  商品列表
     */
    public List<GoodsInfoVO> analyzerKeywordGoods(String keyword){
        IndexSearcher searcher = LuceneUtil.buildIndexSearcher();
        String key = IndexConstants.GOODS_NAME;
        // 对关键词分词
        JiebaSegmenter jiebaSegmenter = new JiebaSegmenter();
        List<String> keywords = jiebaSegmenter.sentenceProcess(keyword);
        BoolQueryBuilders builders = new BoolQueryBuilders();
        for (String word : keywords) {
            // 将分词后的关键词加入should条件
            builders.should(new FuzzyQuery(new Term(key, word), 2));
        }
        Query query = builders.build();
        // 评论数降序
        Sort sort = new Sort(new SortField(IndexConstants.GOODS_COMMENT, SortField.Type.INT, false));
        try{
            TopDocs topDocs = searcher.search(query, SearchConstants.SEARCH_SIZE, sort);
            return entityCreation.docConvertVO(topDocs, searcher);
        } catch (IOException | ParseException e) {
            log.error("[4244.analyzerKeywordGoods error] : {}, msg = {}", ErrorEnum.SEARCH_ERROR.getMsg(), e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * 根据商品小品类名字搜索商品
     * @param typeName   商品小品类名字
     * @return  商品列表
     */
    public List<GoodsInfoVO> smallTypeGoodsPanel(String typeName){
        IndexSearcher searcher = LuceneUtil.buildIndexSearcher();
        String key = IndexConstants.S_TYPE_NAME;
        Query query = new TermQuery(new Term(key, typeName));
        int size = SearchConstants.SEARCH_SIZE;
        try{
            TopDocs topDocs = searcher.search(query,size);
            return entityCreation.docConvertVO(topDocs, searcher);
        }catch (IOException | ParseException e){
            log.error("[4205.smallTypeGoods error] : {}, msg = {}", ErrorEnum.SEARCH_ERROR.getMsg(), e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * 根据商品大品类名字搜索商品
     * @param typeName   商品大品类名字
     * @return  商品列表
     */
    public List<GoodsInfoVO> bigTypeGoodsPanel(String typeName){
        IndexSearcher searcher = LuceneUtil.buildIndexSearcher();
        Query query = new TermQuery(new Term(IndexConstants.TYPE_NAME, typeName));
        int size = SearchConstants.SEARCH_SIZE;
        try{
            TopDocs topDocs = searcher.search(query,size);
            return entityCreation.docConvertVO(topDocs, searcher);
        }catch (IOException | ParseException e){
            log.error("[4206.bigTypeGoodsPanel error] : {}, msg = {}", ErrorEnum.SEARCH_ERROR.getMsg(), e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * 搜索商品信息
     * @param goodsNumber  商品编号
     * @return  商品信息
     */
    public GoodsInfoVO getGoodsInfoByNumber(String goodsNumber){
        IndexSearcher searcher = LuceneUtil.buildIndexSearcher();
        Query query = new TermQuery(new Term(IndexConstants.GOODS_NUMBER, goodsNumber));
        try{
            TopDocs topDocs = searcher.search(query, 1);
            ScoreDoc[] scoreDoc = topDocs.scoreDocs;
            return entityCreation.objConvertVO(scoreDoc[0], searcher);
        }catch (IOException | ParseException e){
            log.error("[4206.getGoodsInfoByNumber error] : {}, msg = {}", ErrorEnum.SEARCH_ERROR.getMsg(), e.getMessage());
        }
        return null;
    }

}
