package com.kuaipin.search.server.convert;

import cn.hutool.core.date.DateUtil;
import com.kuaipin.search.server.constants.IndexConstants;
import com.kuaipin.search.server.entity.response.GoodsInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
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

    public List<GoodsInfoVO> docConvertVO(TopDocs topDocs, IndexSearcher searcher) throws ParseException {
        List<GoodsInfoVO> goodsInfoVOList = new ArrayList<>();
        for (ScoreDoc topDoc : topDocs.scoreDocs) {
            Document document = new Document();
            try{
                document = searcher.doc(topDoc.doc);
            }catch (IOException e){
                log.error("[5001.docConvertVO error]: 获取不到文档");
            }
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
            goodsInfoVO.setSTypeId(Long.parseLong(document.get(IndexConstants.S_TYPE_ID)));
            goodsInfoVO.setSTypeName(document.get(IndexConstants.S_TYPE_NAME));
            goodsInfoVO.setTypeName(document.get(IndexConstants.TYPE_NAME));
            goodsInfoVO.setCreateTime(DateTools.stringToDate(document.get(IndexConstants.CREATE_TIME)));
            goodsInfoVOList.add(goodsInfoVO);
        }
        return goodsInfoVOList;
    }

}
