package com.kuaipin.search.server.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.kuaipin.common.util.IdUtils;
import com.kuaipin.search.server.SearchApplication;
import com.kuaipin.search.server.entity.Goods;
import com.kuaipin.search.server.entity.po.BusinessInfo;
import com.kuaipin.search.server.entity.po.GoodsInfo;
import com.kuaipin.search.server.entity.po.SmallCategory;
import com.kuaipin.search.server.mapper.GoodsTestMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 创建商品数据Test
 * @Author: ljf
 * @DateTime: 2021/12/16 0:37
 */
@Slf4j
@SuppressWarnings("squid:S2699")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApplication.class)
public class CreateDataServiceTest {

    @Autowired
    private GoodsTestMapper goodsTestMapper;

    public static void createId(){
        for (int i = 0; i < 5; i++) {
            Snowflake snowflake = IdUtil.getSnowflake();
            System.out.println(snowflake.nextId());
        }
    }


    // 添加商品品类数据
    @Test
    public void createGoodsType(){
        List<String> resList = goodsTestMapper.findGoodsTypeTest();
        if (CollectionUtil.isNotEmpty(resList)){
            // 去重
            Set<String> set = new HashSet<>(resList);
            List<SmallCategory> smallCategoryList = new ArrayList<>();
            for (String s : set) {
                if (s.equals("笔记本")){
                    continue;
                }
                SmallCategory smallCategory = new SmallCategory();
                smallCategory.setSTypeName(s);
                smallCategory.setCreateTime(new Date());
                smallCategory.setUpdateTime(new Date());
                smallCategory.setSTypeId(IdUtils.snowflakeId());
                smallCategoryList.add(smallCategory);
            }
            int size = goodsTestMapper.insertSmallCategory(smallCategoryList);
            if (size != smallCategoryList.size()){
                log.error("-----添加数量与成功添加数量不符合！");
            }else{
                log.info("------添加成功");
            }
        }
    }

    // 添加商家数据
    @Test
    public void createBusiness(){
        // 查询所有商家
        List<Goods> goodsList = goodsTestMapper.findAllGoods();
        // 获取商家名字并去重
        Set<String> set = goodsList.stream().map(Goods::getBoss).collect(Collectors.toSet());
        // 获取现有的表中的商家数据
        List<BusinessInfo> newBusinessInfos = goodsTestMapper.findAllBusiness();
        // 存放商家信息
        List<BusinessInfo> businessInfoList = new ArrayList<>();
        for (String s : set) {
            int imp = 1;
            for (BusinessInfo businessInfo : newBusinessInfos) {
                if (s != null && s.equals(businessInfo.getBusinessName())) {
                    imp = 0;
                    break;
                }
            }
            // 现有商家表中不存在该商家则添加
            if (imp != 0){
                BusinessInfo businessInfo = new BusinessInfo();
                businessInfo.setBusinessId(IdUtils.snowflakeId())
                        .setIsAuthentication(1)
                        .setCreateTime(new Date())
                        .setUpdateTime(new Date());
                // 从商家名字判断是否是旗舰店
                if (s != null && s.contains("旗舰店")){
                    businessInfo.setIsFlagship(1);
                }else{
                    businessInfo.setIsFlagship(0);
                }
                // 名字为空则设置为官方旗舰店
                if (s == null){
                    businessInfo.setBusinessName("官方自营旗舰店");
                    businessInfo.setIsFlagship(1);
                }else{
                    businessInfo.setBusinessName(s);
                }
                businessInfoList.add(businessInfo);
            }
        }
        // 添加商家信息
        int insertSum = goodsTestMapper.insertBusinessInfo(businessInfoList);
        log.info("----添加成功，共添加" + insertSum + "条");
    }

    // 添加商品数据
    @Test
    public void createGoodsInfo(){
        // 查询所00有品类信0息0
        List<SmallCategory> smallCategoryList = goodsTestMapper.findAllSmallCategory();
        // 存放品类和品类id
        Map<String, Object> categoryMap = new HashMap<>();
        for (SmallCategory smallCategory : smallCategoryList) {
            categoryMap.put(smallCategory.getSTypeName(), smallCategory.getSTypeId());
        }
        // 查询所有商家信息
        List<BusinessInfo> businessInfoList = goodsTestMapper.findAllBusiness();
        // 存放商家名字和商家id
        Map<String, Object> businessMap = new HashMap<>();
        for (BusinessInfo businessInfo : businessInfoList) {
            businessMap.put(businessInfo.getBusinessName(), businessInfo.getBusinessId());
        }
        // 查询所有爬虫商品信息
        List<Goods> goodsList = goodsTestMapper.findAllGoods();
        // 对商品集合根据商品名字去重
        List<Goods> goodsRepeatList = goodsList.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Goods::getName))), ArrayList::new
        ));
        // 存放商品信息
        List<GoodsInfo> goodsInfoList = new ArrayList<>();
        for (Goods goods : goodsRepeatList) {
            // 查询现有数据库中是否存在该商品
            String name = goodsTestMapper.findOldGoods(goods.getName());
            if (name != null){
                // 不为空，则不添加该商品
                continue;
            }
            // 获取并设置商品信息实体属性值
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setGoodsId(IdUtils.objectId())
                    .setGoodsNumber(IdUtils.snowflakeId())
                    .setGoodsName(goods.getName())
                    .setGoodsBrand(goods.getBrand() == null ? "未知" : goods.getBrand())
                    .setSTypeId((Long) categoryMap.get(goods.getType()))
                    .setGoodsPic(goods.getPic())
                    .setBusinessId((Long) businessMap.get(goods.getBoss() == null ? "官方自营旗舰店" : goods.getBoss()))
                    .setGoodsComment((int) (Math.random()*(99999-1000) + 1000))
                    .setGoodsPrice(null)
                    .setCreateTime(new Date())
                    .setUpdateTime(new Date());
            // 去除商品规格的括号
            String edition = goods.getEdition().replace("[", "");
            edition = edition.replace("]", "");
            goodsInfo.setGoodsEdition(edition);
            goodsInfoList.add(goodsInfo);
        }
        int insertNum = goodsTestMapper.insertGoodsInfo(goodsInfoList);
        log.info("---添加成功,一共" + insertNum + "条");
    }

    /**
     * 根据商品品类定义价格
     * @param type  品类
     * @return  价格
     */
    public static BigDecimal getPrice(String type){
        if (type.equals("手机壳") || type.equals("钢化膜") || type.equals("充电器")){
            BigDecimal b1 = new BigDecimal("30.00");
            BigDecimal b2 = new BigDecimal("150.00");
            return getRandom(b1, b2);
        }else if (type.equals("相机")|| type.equals("笔记本")){
            BigDecimal b1 = new BigDecimal("3000.00");
            BigDecimal b2 = new BigDecimal("12000.00");
            return getRandom(b1, b2);
        }else if (type.equals("男士风衣") || type.equals("机械键盘")|| type.equals("鼠标")|| type.equals("休闲西装")
                || type.equals("男士外套") || type.equals("夹克") || type.equals("卫衣女")
                || type.equals("连衣裙") || type.equals("针织衫女")){
            BigDecimal b1 = new BigDecimal("100.00");
            BigDecimal b2 = new BigDecimal("600.00");
            return getRandom(b1, b2);
        }else if (type.equals("相机镜头")){
            BigDecimal b1 = new BigDecimal("500.00");
            BigDecimal b2 = new BigDecimal("1500.00");
            return getRandom(b1, b2);
        }else if (type.equals("衬衫") || type.equals("工装裤") || type.equals("牛仔裤") || type.equals("男士休闲裤")
                || type.equals("男士短裤") || type.equals("衬衫女") || type.equals("半身裙") || type.equals("T恤女")
                || type.equals("休闲裤女") || type.equals("打底裤女") || type.equals("牛仔裤女") || type.equals("短裙")){
            BigDecimal b1 = new BigDecimal("50.00");
            BigDecimal b2 = new BigDecimal("500.00");
            return getRandom(b1, b2);
        }else{
            BigDecimal b1 = new BigDecimal("500.00");
            BigDecimal b2 = new BigDecimal("2000.00");
            return getRandom(b1, b2);
        }
    }

    public static BigDecimal getRandom(BigDecimal min, BigDecimal max){
        int minF = min.intValue();
        int maxF = max.intValue();
        // 生成随机数
        int n = (int) (Math.random() * (maxF - minF)+ minF);
        String price = String.valueOf(n) + ".00";
        // 返回保留两位小数的随机数
        return new BigDecimal(price);
    }

}
