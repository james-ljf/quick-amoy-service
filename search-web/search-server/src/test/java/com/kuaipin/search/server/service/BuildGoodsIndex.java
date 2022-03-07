package com.kuaipin.search.server.service;

import com.kuaipin.common.constants.SingletonDirectory;
import com.kuaipin.common.util.lucene.LuceneUtil;
import com.kuaipin.search.server.SearchApplication;
import com.kuaipin.search.server.entity.po.GoodsInfo;
import com.kuaipin.search.server.mapper.SearchGoodsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    private SearchGoodsMapper goodsMapper;

    @Test
    public void indexTest(){
        // 获取所有商品数据
        List<GoodsInfo> goodsInfoList = goodsMapper.findGoodsInfo();
        log.info("共获取商品数据" + goodsInfoList.size() + "条");
        // 创建索引
        Directory directory = SingletonDirectory.buildFsDirectory();
        IndexWriter indexWriter = LuceneUtil.buildIndexWriter(directory);
        // 索引存放的文档
        List<Document> documents = new ArrayList<>();
        for (GoodsInfo goodsInfo : goodsInfoList) {
            Document document = new Document();
            document.add(new StringField("goods_number", goodsInfo.getGoodsNumber().toString(), Field.Store.YES));
            document.add(new StringField("goods_name", goodsInfo.getGoodsName(), Field.Store.YES));
        }
    }

}
