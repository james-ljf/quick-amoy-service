package com.kuaipin.search.server.external;

import com.kuaipin.common.constants.ErrorEnum;
import com.kuaipin.search.server.constants.IndexConstants;
import com.kuaipin.search.server.constants.SearchConstants;
import com.kuaipin.search.server.convert.EntityCreation;
import com.kuaipin.search.server.entity.response.GoodsInfoVO;
import com.kuaipin.search.server.util.LuceneUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.Highlighter;
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

    @Autowired
    private EntityCreation entityCreation;

    private static final int MAX_EDITS = 2;

    /**
     * 根据关键词模糊搜索商品名称
     * @param keyword   关键词
     * @return  商品列表
     */
    public List<GoodsInfoVO> goodsNameRecall(String keyword){
        IndexReader reader = LuceneUtil.buildIndexReader();
        IndexSearcher searcher = LuceneUtil.buildIndexSearcher(reader);
        String key = IndexConstants.GOODS_NAME;
        // 商品名模糊搜索，最大间距为2
        Query query = new FuzzyQuery(new Term(key, keyword), MAX_EDITS);
        // 评论数降序
        Sort sort = new Sort(new SortField(IndexConstants.GOODS_COMMENT, SortField.Type.INT, false));
        try{
            TopDocs topDocs = searcher.search(query, SearchConstants.SEARCH_SIZE, sort);
            Highlighter highlighter = LuceneUtil.getHighlighter(query, key);
            Analyzer analyzer = new SmartChineseAnalyzer();
            return entityCreation.docConvertVoHigh(topDocs, searcher, analyzer, highlighter, key);
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
        IndexReader reader = LuceneUtil.buildIndexReader();
        IndexSearcher searcher = LuceneUtil.buildIndexSearcher(reader);
        // 商家名称模糊搜索，最大间距为2
        Query query = new FuzzyQuery(new Term(IndexConstants.BUSINESS_NAME, keyword), MAX_EDITS);
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
     * 根据关键词模糊搜索商品小品类名称
     * @param keyword   关键词
     * @return  商品列表
     */
    public List<GoodsInfoVO> sTypeNameRecall(String keyword){
        IndexReader reader = LuceneUtil.buildIndexReader();
        IndexSearcher searcher = LuceneUtil.buildIndexSearcher(reader);
        String key = IndexConstants.S_TYPE_NAME;
        // 商品小品类名称模糊搜索，最大间距为2
        Query query = new FuzzyQuery(new Term(key, keyword), MAX_EDITS);
        // 评论数降序
        Sort sort = new Sort(new SortField(IndexConstants.GOODS_COMMENT, SortField.Type.INT, false));
        try{
            TopDocs topDocs = searcher.search(query, SearchConstants.SEARCH_SIZE, sort);
            Highlighter highlighter = LuceneUtil.getHighlighter(query, key);
            Analyzer analyzer = new SmartChineseAnalyzer();
            return entityCreation.docConvertVoHigh(topDocs, searcher, analyzer, highlighter, key);
        }catch (IOException | ParseException | InvalidTokenOffsetsException e){
            log.error("[4203.sTypeNameRecall error] : {}, msg = {}", ErrorEnum.SEARCH_ERROR.getMsg(), e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * 根据关键词模糊搜索商品品牌名称
     * @param keyword   关键词
     * @return  商品列表
     */
    public List<GoodsInfoVO> goodsBrandRecall(String keyword){
        IndexReader reader = LuceneUtil.buildIndexReader();
        IndexSearcher searcher = LuceneUtil.buildIndexSearcher(reader);
        String key = IndexConstants.GOODS_BRAND;
        // 商品品牌模糊搜索，最大间距为2
        Query query = new FuzzyQuery(new Term(key, keyword), MAX_EDITS);
        // 评论数降序
        Sort sort = new Sort(new SortField(IndexConstants.GOODS_COMMENT, SortField.Type.INT, false));
        try{
            TopDocs topDocs = searcher.search(query, SearchConstants.SEARCH_SIZE, sort);
            Highlighter highlighter = LuceneUtil.getHighlighter(query, key);
            Analyzer analyzer = new SmartChineseAnalyzer();
            return entityCreation.docConvertVoHigh(topDocs, searcher, analyzer, highlighter, key);
        }catch (IOException | ParseException | InvalidTokenOffsetsException e){
            log.error("[4204.goodsBrandRecall error] : {}, msg = {}", ErrorEnum.SEARCH_ERROR.getMsg(), e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * 根据商品小品类名字搜索商品
     * @param typeName   商品小品类名字
     * @return  商品列表
     */
    public List<GoodsInfoVO> smallTypeGoodsPanel(String typeName){
        IndexReader reader = LuceneUtil.buildIndexReader();
        IndexSearcher searcher = LuceneUtil.buildIndexSearcher(reader);
        String key = IndexConstants.S_TYPE_NAME;
        Query query = new TermQuery(new Term(key, typeName));
        int size = SearchConstants.SEARCH_SIZE;
        try{
            TopDocs topDocs = searcher.search(query,size);
            Highlighter highlighter = LuceneUtil.getHighlighter(query, key);
            Analyzer analyzer = new SmartChineseAnalyzer();
            return entityCreation.docConvertVoHigh(topDocs, searcher, analyzer, highlighter, key);
        }catch (IOException | ParseException | InvalidTokenOffsetsException e){
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
        IndexReader reader = LuceneUtil.buildIndexReader();
        IndexSearcher searcher = LuceneUtil.buildIndexSearcher(reader);
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

}
