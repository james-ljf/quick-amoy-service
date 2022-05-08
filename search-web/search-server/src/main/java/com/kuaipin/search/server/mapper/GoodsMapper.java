package com.kuaipin.search.server.mapper;


import com.kuaipin.search.server.entity.po.Carousel;
import com.kuaipin.search.server.entity.po.SmallCategory;
import com.kuaipin.search.server.entity.response.GoodsCategoryVO;
import com.kuaipin.search.server.entity.response.GoodsInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: ljf
 * @DateTime: 2022/3/7 12:05
 */
@Mapper
public interface GoodsMapper {

    /**
     * 查询商品
     *
     * @return 商品列表
     */
    List<GoodsInfoVO> findGoodsInfo();

    /**
     * 查询商品品类
     *
     * @return 商品品类列表
     */
    List<GoodsCategoryVO> getGoodsCategory();

    /**
     * 添加商品小品类
     *
     * @param smallCategory 商品小品类
     * @return 添加
     */
    int insertSmallCategory(@Param("sc") SmallCategory smallCategory);

    /**
     * 添加热推商品
     *
     * @param carousel 热推商品信息
     * @return 添加数量
     */
    int insertCarouselGoods(@Param("cg") Carousel carousel);

    /**
     * 删除热推商品
     *
     * @param carouselId 热推id
     * @return 删除数量
     */
    int delCarouselGoods(Long carouselId);

    /**
     * 获取所有热推商品
     * @return  热推商品列表
     */
    List<Carousel> getCarouselGoods();

    /**
     * 查询热推商品
     * @param goodsNumber  商品编号
     * @return  热推商品
     */
    Carousel findCarouselGoods(Long goodsNumber);

}
