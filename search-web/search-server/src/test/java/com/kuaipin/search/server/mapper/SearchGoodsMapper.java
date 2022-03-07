package com.kuaipin.search.server.mapper;

import com.kuaipin.search.server.entity.po.GoodsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: ljf
 * @DateTime: 2022/3/7 12:05
 */
@Mapper
public interface SearchGoodsMapper {

    @Select("select goods_number, goods_name, goods_brand, s_type_id, goods_pic, goods_edition, business_id, goods_comment," +
            "goods_stock, goods_price from goods_info")
    List<GoodsInfo> findGoodsInfo();

}
