package com.kuaipin.search.server.mapper;


import com.kuaipin.search.server.entity.response.GoodsCategoryVO;
import com.kuaipin.search.server.entity.response.GoodsInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: ljf
 * @DateTime: 2022/3/7 12:05
 */
@Mapper
public interface GoodsMapper {

    /**
     * 查询商品
     * @return  商品列表
     */
    List<GoodsInfoVO> findGoodsInfo();

    /**
     * 查询商品品类
     * @return  商品品类列表
     */
    List<GoodsCategoryVO> getGoodsCategory();

}
