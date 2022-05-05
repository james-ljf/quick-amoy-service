package com.kuaipin.search.server.service;

import com.kuaipin.common.entity.Page;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.search.server.entity.po.Carousel;
import com.kuaipin.search.server.entity.po.SmallCategory;
import com.kuaipin.search.server.entity.response.CarouselVO;
import com.kuaipin.search.server.entity.response.GoodsCategoryVO;
import com.kuaipin.search.server.entity.response.GoodsInfoVO;

import java.util.List;

/**
 * 商品操作
 * @Author: ljf
 * @DateTime: 2022/4/20 13:38
 */
public interface GoodsService {

    /**
     * 获取商品品类面板
     * @return  商品品类列表
     */
    List<GoodsCategoryVO> getGoodsCategoryList();

    /**
     * 添加商品小品类
     * @param smallCategory  小品类信息
     * @return  添加数量
     */
    int increaseSmallCategory(SmallCategory smallCategory);

    /**
     * 获取所有商品
     * @param pageDTO  分页请求
     * @return  商品列表
     */
    Page<GoodsInfoVO> getGoodsPanel(PageDTO pageDTO);

    /**
     * 设置热推商品
     * @param carousel  热推商品信息
     * @return  设置数量
     */
    int setGoodsCarousel(Carousel carousel);

    /**
     * 删除热推商品
     * @param carouselId  热推商品id
     * @return  删除数量
     */
    int cancelGoodsCarousel(Long carouselId);

    /**
     * 获取热推商品列表
     * @return  热推商品列表
     */
    List<CarouselVO> carouselGoodsPanel();

}
