package com.kuaipin.search.server.external;

import com.kuaipin.common.constants.ErrorEnum;
import com.kuaipin.search.server.convert.EntityCreation;
import com.kuaipin.search.server.util.BoolQueryBuilders;
import com.kuaipin.search.server.util.LuceneUtil;
import com.kuaipin.search.server.constants.IndexConstants;
import com.kuaipin.search.server.constants.RecommendRuleConstants;
import com.kuaipin.search.server.entity.response.GoodsInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
/**
 * 搜索兜底策略
 *
 * @Author: ljf
 * @DateTime: 2022/3/24 15:24
 */
@Slf4j
@Component
public class CoverBottomComponent {

    private EntityCreation entityCreation;
    @Autowired
    private void setEntityCreation(EntityCreation entityCreation){
        this.entityCreation = entityCreation;
    }

    /**
     * 推荐的兜底策略
     * 召回评论数范围内的商品
     * 排序：根据商品的评论降序排序
     *
     * @return 商品文档 and 搜索器
     */
    public List<GoodsInfoVO> hotBottoming(List<GoodsInfoVO> goodsInfoVOList, int size){
        IndexSearcher searcher = LuceneUtil.buildIndexSearcher();
        // 召回条件(评论数)
        int start = (int) RecommendRuleConstants.START_COMMENT_NUM.getType();
        int end = (int) RecommendRuleConstants.END_COMMENT_NUM.getType();
        // 构造搜索条件(按评论数范围查询)
        BoolQueryBuilders builders = new BoolQueryBuilders();
        Query commentQuery = IntPoint.newRangeQuery(IndexConstants.GOODS_COMMENT, start, end);
        builders.must(commentQuery);
        if (CollectionUtils.isNotEmpty(goodsInfoVOList)){
            // 必要不存在条件
            builders.mustNotAll(goodsInfoVOList);
        }
        Query query = builders.build();
        // 按评论数降序排序
        Sort sort = new Sort(new SortField(IndexConstants.GOODS_COMMENT, SortField.Type.INT, false));
        try{
            // 获取搜索结果
            TopDocs topDocs =  searcher.search(query, size, sort);
            return entityCreation.docConvertVO(topDocs, searcher);
        }catch (IOException | ParseException e){
            log.error("[4021.hotBottoming error] : {}, req = {}", ErrorEnum.SEARCH_ERROR, goodsInfoVOList);
        }
        // 兜底失败返回空数组
        return new ArrayList<>();
    }

}
