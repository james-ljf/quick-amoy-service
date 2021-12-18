package com.kuaipin.search.server.mapper;

import com.kuaipin.search.server.entity.Goods;
import com.kuaipin.search.server.entity.po.BusinessInfo;
import com.kuaipin.search.server.entity.po.GoodsInfo;
import com.kuaipin.search.server.entity.po.SmallCategory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 测试类：读取爬虫商品数据
 * @Author: ljf
 * @DateTime: 2021/12/18 12:55
 */
@Mapper
public interface GoodsTestMapper {

    /**
     * 查询爬虫商品类型
     * @return  商品类型列表
     */
    @Select("select type from goods")
    List<String> findGoodsTypeTest();

    /**
     * 添加小品类到数据库
     * @param smallCategoryList 小品类集合
     * @return  添加数量
     */
    @Insert("<script>" +
            "insert into goods_small_category " +
            "(s_type_id, s_type_name, create_time, update_time, b_type_id) " +
            "values " +
            "<foreach item='sg' collection='list' index='index' open='' close='' separator=','> " +
            "(#{sg.sTypeId}, #{sg.sTypeName}, #{sg.createTime}, #{sg.updateTime}, #{sg.bTypeId}) " +
            "</foreach> " +
            "</script>")
    Integer insertSmallCategory(@Param("list") List<SmallCategory> smallCategoryList);

    /**
     * 添加商品信息
     * @param goodsInfoList 商品信息集合
     * @return  添加数量
     */
    @Insert("<script>" +
            "insert into goods_info " +
            "(goods_id, goods_number, goods_name, goods_brand, s_type_id, goods_pic, goods_edition, business_id, " +
            "goods_comment, goods_price, create_time, update_time) " +
            "values " +
            "<foreach item='g' collection='list' index='index' open='' close='' separator=','> " +
            "(#{g.goodsId}, #{g.goodsNumber}, #{g.goodsName}, #{g.goodsBrand}, #{g.sTypeId}, #{g.goodsPic}, " +
            "#{g.goodsEdition}, #{g.businessId}, #{g.goodsComment}, #{g.goodsPrice}, #{g.createTime}, #{g.updateTime}) " +
            "</foreach> " +
            "</script>")
    Integer insertGoodsInfo(@Param("list") List<GoodsInfo> goodsInfoList);

    /**
     * 添加商家信息
     * @param businessInfoList  商家信息集合
     * @return  添加数量
     */
    @Insert("<script>" +
            "insert into business_info " +
            "(business_id, business_name, is_authentication, is_flagship, create_time, update_time) " +
            "values " +
            "<foreach item='b' collection='list' index='index' open='' close='' separator=','> " +
            "(#{b.businessId}, #{b.businessName}, #{b.isAuthentication}, #{b.isFlagship}, #{b.createTime}, #{b.updateTime}) " +
            "</foreach> " +
            "</script>")
    Integer insertBusinessInfo(@Param("list") List<BusinessInfo> businessInfoList);

    /**
     * 查询所有商家信息
     * @return  商家列表
     */
    @Select("select business_id, business_name from business_info")
    List<BusinessInfo> findAllBusiness();

    /**
     * 查询所有品类
     * @return  品类列表
     */
    @Select("select s_type_id, s_type_name from goods_small_category")
    List<SmallCategory> findAllSmallCategory();

    /**
     * 查询所有爬虫商品数据
     * @return  商品信息列表
     */
    @Select("select name, brand, edition, boss, pic, type from goods")
    List<Goods> findAllGoods();

}
