package com.kuaipin.search.server.convert;

import com.kuaipin.search.server.constants.IndexConstants;
import com.kuaipin.search.server.entity.po.Carousel;
import com.kuaipin.search.server.entity.response.CarouselVO;
import com.kuaipin.search.server.entity.response.GoodsInfoVO;
import com.kuaipin.search.server.util.LuceneUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * 索引---实体类对应类
 * @Author: ljf
 * @DateTime: 2022/3/22 17:06
 */
@Slf4j
@Component
public class EntityCreation {

    public List<GoodsInfoVO> docConvertVO(TopDocs topDocs, IndexSearcher searcher) throws ParseException, IOException {
        List<GoodsInfoVO> goodsInfoVOList = new ArrayList<>();
        for (ScoreDoc topDoc : topDocs.scoreDocs) {
            Document document = searcher.doc(topDoc.doc);
            // 创建商品信息类，从索引构建实体并返回
            GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
            goodsInfoVO.setGoodsId(document.get(IndexConstants.GOODS_ID));
            goodsInfoVO.setGoodsNumber(Long.parseLong(document.get(IndexConstants.GOODS_NUMBER)));
            goodsInfoVO.setGoodsName(document.get(IndexConstants.GOODS_NAME));
            goodsInfoVO.setGoodsBrand(document.get(IndexConstants.GOODS_BRAND));
            goodsInfoVO.setGoodsPic(document.get(IndexConstants.GOODS_PIC));
            goodsInfoVO.setGoodsEdition(document.get(IndexConstants.GOODS_EDITION));
            goodsInfoVO.setGoodsPrice(document.get(IndexConstants.GOODS_PRICE));
            goodsInfoVO.setGoodsComment(Integer.parseInt(document.get(IndexConstants.GOODS_COMMENT)));
            goodsInfoVO.setBusinessId(Long.parseLong(document.get(IndexConstants.BUSINESS_ID)));
            goodsInfoVO.setBusinessName(document.get(IndexConstants.BUSINESS_NAME));
            goodsInfoVO.setIsFlagship(document.get(IndexConstants.IS_FLAGSHIP));
            goodsInfoVO.setSTypeId(Long.parseLong(document.get(IndexConstants.S_TYPE_ID)));
            goodsInfoVO.setSTypeName(document.get(IndexConstants.S_TYPE_NAME));
            goodsInfoVO.setTypeName(document.get(IndexConstants.TYPE_NAME));
            goodsInfoVO.setCreateTime(DateTools.stringToDate(document.get(IndexConstants.CREATE_TIME)));
            goodsInfoVOList.add(goodsInfoVO);
        }
        return goodsInfoVOList;
    }

    /**
     * 在结果中高亮显示关键词
     * @param topDocs   搜索结果文档
     * @param searcher  搜索器
     * @param key   对应的key
     * @return  高亮商品列表结果
     */
    public List<GoodsInfoVO> docConvertVoHigh(TopDocs topDocs, IndexSearcher searcher, String key, String keyword) throws ParseException, IOException {
        List<GoodsInfoVO> goodsInfoVOList = new ArrayList<>();
        for (ScoreDoc topDoc : topDocs.scoreDocs) {
            Document document = searcher.doc(topDoc.doc);
            // 创建商品信息类，从索引构建实体并返回
            GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
            goodsInfoVO.setGoodsId(document.get(IndexConstants.GOODS_ID));
            goodsInfoVO.setGoodsNumber(Long.parseLong(document.get(IndexConstants.GOODS_NUMBER)));
            goodsInfoVO.setGoodsName(document.get(IndexConstants.GOODS_NAME));
            goodsInfoVO.setGoodsBrand(document.get(IndexConstants.GOODS_BRAND));
            goodsInfoVO.setGoodsPic(document.get(IndexConstants.GOODS_PIC));
            goodsInfoVO.setGoodsEdition(document.get(IndexConstants.GOODS_EDITION));
            goodsInfoVO.setGoodsPrice(document.get(IndexConstants.GOODS_PRICE));
            goodsInfoVO.setGoodsComment(Integer.parseInt(document.get(IndexConstants.GOODS_COMMENT)));
            goodsInfoVO.setBusinessId(Long.parseLong(document.get(IndexConstants.BUSINESS_ID)));
            goodsInfoVO.setBusinessName(document.get(IndexConstants.BUSINESS_NAME));
            goodsInfoVO.setIsFlagship(document.get(IndexConstants.IS_FLAGSHIP));
            goodsInfoVO.setSTypeId(Long.parseLong(document.get(IndexConstants.S_TYPE_ID)));
            goodsInfoVO.setSTypeName(document.get(IndexConstants.S_TYPE_NAME));
            goodsInfoVO.setTypeName(document.get(IndexConstants.TYPE_NAME));
            goodsInfoVO.setCreateTime(DateTools.stringToDate(document.get(IndexConstants.CREATE_TIME)));
            // 关键词高亮
            String goodsVal = document.get(key);
            if (IndexConstants.GOODS_NAME.equals(key)){
                String newName = goodsVal.replaceAll(keyword, "<span style='color:red'>" + keyword + "</span>");
                goodsInfoVO.setGoodsName(newName);
            }
            goodsInfoVOList.add(goodsInfoVO);
        }
        return goodsInfoVOList;
    }

    /**
     * 热推商品类转换
     * @param carousel  热推商品实体
     * @return  VO
     */
    public CarouselVO carouselConvertVO(Carousel carousel){
        CarouselVO carouselVO = new CarouselVO();
        carouselVO.setCarouselId(carousel.getCarouselId());
        carouselVO.setGoodsName(carousel.getGoodsName());
        carouselVO.setGoodsNumber(carousel.getGoodsNumber());
        carouselVO.setGoodsPic(carousel.getGoodsPic());
        carouselVO.setCreateTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(carousel.getCreateTime()), ZoneId.systemDefault()));
        carouselVO.setUpdateTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(carousel.getUpdateTime()), ZoneId.systemDefault()));
        return carouselVO;
    }

}
