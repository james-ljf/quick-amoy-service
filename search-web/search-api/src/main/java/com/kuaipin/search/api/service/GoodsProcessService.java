package com.kuaipin.search.api.service;

import com.kuaipin.common.annotation.CallableAPI;
import com.kuaipin.common.entity.Page;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.search.api.entity.dto.CarouselDTO;
import com.kuaipin.search.api.entity.dto.CarouselRequestDTO;
import com.kuaipin.search.api.entity.dto.GoodsCategoryDTO;
import com.kuaipin.search.api.entity.dto.GoodsDTO;

import java.util.List;

/**
 * @Author: ljf
 * @DateTime: 2022/5/7 10:22
 */
public interface GoodsProcessService {

    /**
     * 获取商品品类面板
     * @return  商品品类列表
     */
    @CallableAPI(desc = "获取商品品类面板")
    List<GoodsCategoryDTO> goodsCategoryList();

    /**
     * 获取所有商品
     * @param pageDTO  分页请求
     * @return  商品列表
     */
    @CallableAPI(desc = "查询所有商品")
    Page<GoodsDTO> allGoodsPanel(PageDTO pageDTO);

    /**
     * 设置热推商品
     * @param requestDTO  热推商品信息
     * @return  设置数量
     */
    @CallableAPI(desc = "设置首页轮播图热推商品")
    int setGoodsCarousel(CarouselRequestDTO requestDTO);

    /**
     * 删除热推商品
     * @param carouselId  热推商品id
     * @return  删除数量
     */
    @CallableAPI(desc = "删除首页轮播图热推商品")
    int cancelGoodsCarousel(Long carouselId);

    /**
     * 获取热推商品列表
     * @return  热推商品列表
     */
    @CallableAPI(desc = "获取首页轮播图热推商品")
    List<CarouselDTO> carouselGoodsPanel();

}
