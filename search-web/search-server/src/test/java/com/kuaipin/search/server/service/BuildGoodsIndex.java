package com.kuaipin.search.server.service;

import com.alibaba.fastjson.JSONObject;
import com.kuaipin.search.server.constants.singleton.SingletonDirectory;
import com.kuaipin.search.server.constants.singleton.SingletonSuggest;
import com.kuaipin.search.server.entity.Goods;
import com.kuaipin.search.server.util.LuceneUtil;
import com.kuaipin.search.server.SearchApplication;
import com.kuaipin.search.server.entity.GoodsInfoTest;
import com.kuaipin.search.server.entity.KeywordIterator;
import com.kuaipin.search.server.mapper.SearchGoodsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.Directory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

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

    /**
     * 建立索引
     */
    @Test
    public void indexTest() throws IOException {
        // 获取所有商品数据
        List<GoodsInfoTest> goodsInfoList = goodsMapper.findGoodsInfo();
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
            document.add(new StringField("business_name", goodsInfo.getBusinessName(), Field.Store.YES));
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

    /**
     * 构建搜索联想词典
     * key : 商品小品类
     */
    @Test
    public void suggestTest() throws IOException {
        // 获取所有商品数据
        List<GoodsInfoTest> goodsInfoList = goodsMapper.findGoodsInfo();
        log.info("共获取商品数据" + goodsInfoList.size() + "条");
        AnalyzingInfixSuggester suggester = SingletonSuggest.buildSuggester();
        // 构建商品小品类词典
        List<GoodsInfoTest> sTypeList = goodsInfoList.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(GoodsInfoTest::getSTypeName))), ArrayList::new
        ));
        List<JSONObject> objectList = new ArrayList<>();
        for (GoodsInfoTest goodsInfoTest : sTypeList) {
            JSONObject object = new JSONObject();
            object.put("payload", goodsInfoTest.getSTypeId());
            object.put("key", goodsInfoTest.getSTypeName());
            object.put("weight", goodsInfoTest.getGoodsComment());
            objectList.add(object);
        }
        suggester.build(new KeywordIterator(objectList.iterator()));
        suggester.commit();
        // 构建商品品牌词典
        List<GoodsInfoTest> brandList = goodsInfoList.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(GoodsInfoTest::getGoodsBrand))), ArrayList::new
        ));
        List<JSONObject> objects = new ArrayList<>();
        for (GoodsInfoTest goodsInfoTest : brandList) {
            JSONObject object = new JSONObject();
            object.put("key", goodsInfoTest.getGoodsBrand());
            object.put("weight", goodsInfoTest.getGoodsComment());
            objects.add(object);
        }
        suggester.build(new KeywordIterator(objects.iterator()));
        suggester.commit();
        List<GoodsInfoTest> nameList = goodsInfoList.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(GoodsInfoTest::getGoodsName))), ArrayList::new
        ));
        List<JSONObject> objectArrayList = new ArrayList<>();
        for (GoodsInfoTest goodsInfoTest : nameList) {
            JSONObject object = new JSONObject();
            object.put("payload", goodsInfoTest.getGoodsId());
            object.put("key", goodsInfoTest.getGoodsName());
            object.put("weight", goodsInfoTest.getGoodsComment());
            objectArrayList.add(object);
        }
        suggester.build(new KeywordIterator(objectArrayList.iterator()));
        suggester.commit();
        suggester.close();
    }


}
