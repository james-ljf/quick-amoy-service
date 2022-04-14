package com.kuaipin.search.server.service;

import com.kuaipin.search.server.constants.singleton.SingletonDirectory;
import com.kuaipin.search.server.mapper.SearchGoodsMapper;
import com.kuaipin.search.server.util.LuceneUtil;
import com.kuaipin.search.server.SearchApplication;
import com.kuaipin.search.server.entity.GoodsInfoTest;
import com.kuaipin.search.server.mapper.GoodsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ljf
 * @DateTime: 2022/3/7 12:14
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApplication.class)
public class BuildGoodsIndex {

    @Autowired
    private SearchGoodsMapper searchGoodsMapper;

    /**
     * 建立索引
     */
    @Test
    public void indexTest() throws IOException {
        // 获取所有商品数据
        List<GoodsInfoTest> goodsInfoList = searchGoodsMapper.findGoodsInfo();
        log.info("共获取商品数据" + goodsInfoList.size() + "条");
        // 创建索引
        Directory directory = SingletonDirectory.buildFsDirectory();
        IndexWriter indexWriter = LuceneUtil.buildIndexWriter(directory);
        // 索引存放的文档
        List<Document> documents = new ArrayList<>();
        // 建立索引文档
        for (GoodsInfoTest goodsInfo : goodsInfoList) {
            Document document = new Document();
            document.add(new StringField("goods_id", goodsInfo.getGoodsId(), Field.Store.YES));
            document.add(new StringField("goods_number", goodsInfo.getGoodsNumber().toString(), Field.Store.YES));
            document.add(new TextField("goods_name", goodsInfo.getGoodsName(), Field.Store.YES));
            document.add(new StringField("goods_brand", goodsInfo.getGoodsBrand(), Field.Store.YES));
            document.add(new StringField("goods_pic", goodsInfo.getGoodsPic(), Field.Store.YES));
            document.add(new StringField("goods_edition", goodsInfo.getGoodsEdition(), Field.Store.YES));
            document.add(new StringField("s_type_id", goodsInfo.getSTypeId().toString(), Field.Store.YES));
            document.add(new StringField("business_id", goodsInfo.getBusinessId().toString(), Field.Store.YES));
            document.add(new IntPoint("goods_price", Double.valueOf(goodsInfo.getGoodsPrice()).intValue()));
            document.add(new NumericDocValuesField("goods_price", Double.valueOf(goodsInfo.getGoodsPrice()).intValue()));
            document.add(new StringField("goods_price", goodsInfo.getGoodsPrice(), Field.Store.YES));
            document.add(new IntPoint("goods_comment", goodsInfo.getGoodsComment()));
            document.add(new NumericDocValuesField("goods_comment", goodsInfo.getGoodsComment()));
            document.add(new StringField("goods_comment", String.valueOf(goodsInfo.getGoodsComment()), Field.Store.YES));
            document.add(new TextField("business_name", goodsInfo.getBusinessName(), Field.Store.YES));
            document.add(new StringField("is_flagship", goodsInfo.getIsFlagship().toString(), Field.Store.YES));
            document.add(new StringField("s_type_name", goodsInfo.getSTypeName(), Field.Store.YES));
            document.add(new StringField("type_name", goodsInfo.getTypeName(), Field.Store.YES));
            document.add(new StringField("create_time", DateTools.dateToString(goodsInfo.getCreateTime(), DateTools.Resolution.DAY), Field.Store.YES));
            documents.add(document);
        }

        indexWriter.addDocuments(documents);
        indexWriter.flush();
        indexWriter.commit();
        LuceneUtil.close(indexWriter);
        log.info("[200.buildIndex]: 建立索引成功");
        documents.forEach(System.out::println);
    }




}
