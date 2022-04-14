package com.kuaipin.search.server.util;

import com.kuaipin.search.server.constants.IndexConstants;
import com.kuaipin.search.server.entity.response.GoodsInfoVO;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * 布尔组合查询子条件构造类
 * @Author ljf
 * @Date 2021/11/18 11:44
 */
public class BoolQueryBuilders {

    private final List<Query> shouldQueries = new ArrayList<>();
    private final List<Query> mustQueries = new ArrayList<>();
    private final List<Query> mustNotQueries = new ArrayList<>();
    private final List<Query> filterQueries = new ArrayList<>();

    /**
     * 用于应出现在匹配文档中的子句。 对于没有MUST子句的 BooleanQuery，一个或多个SHOULD子句必须匹配一个文档，BooleanQuery 才能匹配
     * @param query 查询条件
     */
    public void should(Query query){
        shouldQueries.add(query);
    }

    /**
     * 用于必须出现在匹配文档中的子句。
     * @param query 查询条件
     */
    public void must(Query query){
        mustQueries.add(query);
    }

    /**
     * 用于不得出现在匹配文档中的子句。 请注意，无法搜索仅包含MUST_NOT子句的查询
     * @param query 查询条件
     */
    public void mustNot(Query query){
        mustNotQueries.add(query);
    }

    /**
     * 像MUST一样，除了这些子句不参与评分
     * @param query 查询条件
     */
    public void filter(Query query){
        filterQueries.add(query);
    }

    /**
     * 构造商品搜索的必要不存在条件
     * @param goodsInfoVOList   商品信息集合
     */
    public void mustNotAll(List<GoodsInfoVO> goodsInfoVOList){
        for (GoodsInfoVO goodsInfoVO : goodsInfoVOList) {
            mustNotQueries.add(new TermQuery(new Term(IndexConstants.GOODS_ID, goodsInfoVO.getGoodsId())));
        }
    }

    /**
     * 将子条件放入布尔条件构造器
     * @return BooleanQuery
     */
    public BooleanQuery build(){
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        for (Query shouldQuery : shouldQueries) {
            builder.add(shouldQuery, BooleanClause.Occur.SHOULD);
        }
        for (Query mustQuery : mustQueries) {
            builder.add(mustQuery, BooleanClause.Occur.MUST);
        }
        for (Query mustNotQuery : mustNotQueries) {
            builder.add(mustNotQuery, BooleanClause.Occur.MUST_NOT);
        }
        for (Query filterQuery : filterQueries) {
            builder.add(filterQuery, BooleanClause.Occur.FILTER);
        }
        return builder.build();
    }

}