package com.kuaipin.search.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kuaipin.search.server.constants.singleton.SingletonSuggest;
import com.kuaipin.search.server.entity.KeywordIterator;
import com.kuaipin.search.server.entity.response.GoodsInfoVO;
import com.kuaipin.search.server.mapper.GoodsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * 服务初始化业务
 * @Author: ljf
 * @DateTime: 2022/4/8 22:03
 */
@Order(-1)
@Slf4j
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void run(String... args) throws Exception {
        suggestTest();
        log.info("执行初始化业务成功......");
    }

    /**
     * 构建搜索联想词典
     * key : 商品小品类
     */
    public void suggestTest() throws IOException {
        // 获取所有商品数据
        List<GoodsInfoVO> goodsInfoList = goodsMapper.findGoodsInfo();
        log.info("共获取商品数据" + goodsInfoList.size() + "条");
        AnalyzingInfixSuggester suggester = SingletonSuggest.buildSuggester();
        // 构建商品小品类词典
        List<GoodsInfoVO> sTypeList = goodsInfoList.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(GoodsInfoVO::getSTypeName))), ArrayList::new
        ));
        List<JSONObject> objectList = new ArrayList<>();
        for (GoodsInfoVO goodsInfoTest : sTypeList) {
            JSONObject object = new JSONObject();
            object.put("payload", goodsInfoTest.getSTypeId());
            object.put("key", goodsInfoTest.getSTypeName());
            long comment = Long.parseLong(goodsInfoTest.getGoodsComment() + "");
            object.put("weight", comment);
            objectList.add(object);
        }
        suggester.build(new KeywordIterator(objectList.iterator()));
        // 构建商品品牌词典
        List<GoodsInfoVO> brandList = goodsInfoList.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(GoodsInfoVO::getGoodsBrand))), ArrayList::new
        ));
        List<JSONObject> objects = new ArrayList<>();
        for (GoodsInfoVO goodsInfoTest : brandList) {
            JSONObject object = new JSONObject();
            object.put("key", goodsInfoTest.getGoodsBrand());
            long comment = Long.parseLong(goodsInfoTest.getGoodsComment() + "");
            object.put("weight", comment);
            objects.add(object);
        }
        suggester.build(new KeywordIterator(objects.iterator()));
        List<GoodsInfoVO> nameList = goodsInfoList.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(GoodsInfoVO::getGoodsName))), ArrayList::new
        ));
        List<JSONObject> objectArrayList = new ArrayList<>();
        for (GoodsInfoVO goodsInfoTest : nameList) {
            JSONObject object = new JSONObject();
            object.put("payload", goodsInfoTest.getGoodsId());
            object.put("key", goodsInfoTest.getGoodsName());
            long comment = Long.parseLong(goodsInfoTest.getGoodsComment() + "");
            object.put("weight", comment);
            objectArrayList.add(object);
        }
        suggester.build(new KeywordIterator(objectArrayList.iterator()));
        suggester.commit();
    }

}
