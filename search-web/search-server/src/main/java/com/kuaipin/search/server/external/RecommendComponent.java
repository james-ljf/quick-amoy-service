package com.kuaipin.search.server.external;

import com.kuaipin.common.entity.Response;
import com.kuaipin.common.util.lucene.BoolQueryBuilders;
import com.kuaipin.common.util.lucene.LuceneUtil;
import com.kuaipin.search.server.constants.IndexConstants;
import com.kuaipin.search.server.convert.EntityCreation;
import com.kuaipin.search.server.entity.response.GoodsInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 推荐列表业务组件
 * @Author: ljf
 * @DateTime: 2022/3/22 14:58
 */
@Slf4j
@Component
public class RecommendComponent {

    @Autowired
    private EntityCreation entityCreation;

    /**
     * 召回评论数在50000以上的商品
     * @return  商品列表
     */
    public List<GoodsInfoVO> getHotGoods(){
        IndexReader reader = LuceneUtil.buildIndexReader();
        IndexSearcher searcher = LuceneUtil.buildIndexSearcher(reader);
        // 召回条件
        Query query = IntPoint.newRangeQuery(IndexConstants.GOODS_COMMENT, 70000, 100000);
        try{
            TopDocs topDocs =  searcher.search(query, 10, Sort.RELEVANCE);
            return entityCreation.docConvertVO(topDocs, searcher);
        }catch (IOException | ParseException e) {
            log.error("[400.getHotGoods error]: error = {}", e.toString());
            return new ArrayList<>();
        }
    }

    /**
     * 召回含有“新品”、“潮流”关键词的商品
     * @return  商品列表
     */
    public List<Document> newTrendGoods(){
        IndexReader reader = LuceneUtil.buildIndexReader();
        IndexSearcher searcher = LuceneUtil.buildIndexSearcher(reader);
        BoolQueryBuilders builders = new BoolQueryBuilders();
        // 召回条件
        builders.should(new TermQuery(new Term(IndexConstants.GOODS_NAME, "新品")));
        builders.should(new TermQuery(new Term(IndexConstants.GOODS_NAME, "潮流")));
        BooleanQuery query = builders.build();
        try{
            TopDocs topDocs =  searcher.search(query, 10, Sort.RELEVANCE);
            List<Document> documents = new ArrayList<>();
            for (ScoreDoc topDoc : topDocs.scoreDocs) {
                Document document = searcher.doc(topDoc.doc);
                documents.add(document);
            }
            return documents;
        }catch (IOException e) {
            log.error("[400.getHotGoods error]: error = {}", e.toString());
            return null;
        }
    }


}
