package com.kuaipin.search.server.mapper;

import com.kuaipin.search.server.entity.GoodsInfoTest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: ljf
 * @DateTime: 2022/3/7 12:05
 */
@Mapper
public interface SearchGoodsMapper {

    @Select("SELECT g.goods_id, g.goods_number, g.goods_name, g.goods_brand, g.s_type_id," +
            " g.goods_pic, g.goods_edition, g.business_id, g.goods_comment, g.goods_price, g.create_time," +
            " b.business_name, gs.s_type_name, gb.type_name " +
            "FROM goods_info as g " +
            "left join business_info as b on g.business_id = b.business_id  " +
            "left join goods_small_category as gs on g.s_type_id = gs.s_type_id " +
            "left join goods_big_category as gb on gs.b_type_id = gb.type_id")
    List<GoodsInfoTest> findGoodsInfo();

}
