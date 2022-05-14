package com.kuaipin.admin.service;

import com.kuaipin.common.entity.Page;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.search.api.entity.dto.CarouselDTO;
import com.kuaipin.search.api.entity.dto.CarouselRequestDTO;
import com.kuaipin.search.api.entity.dto.GoodsCategoryDTO;
import com.kuaipin.search.api.entity.dto.GoodsDTO;
import com.kuaipin.user.api.entity.BrowseRecordDTO;
import com.kuaipin.user.api.entity.SearchRecordDTO;
import com.kuaipin.user.api.entity.UserDTO;

import java.util.List;

/**
 * @Author: ljf
 * @DateTime: 2022/5/7 11:01
 */
public interface SystemService {

    /**
     * 获取商品品类面板
     * @return  商品品类列表
     */
    List<GoodsCategoryDTO> goodsCategoryList();

    /**
     * 获取所有商品
     * @param pageDTO  分页请求
     * @return  商品列表
     */
    Page<GoodsDTO> goodsPanel(PageDTO pageDTO);

    /**
     * 设置热推商品
     * @param requestDTO  热推商品信息
     * @return  设置数量
     */
    int setGoodsCarousel(CarouselRequestDTO requestDTO);

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
    List<CarouselDTO> carouselGoodsPanel();

    /**
     * 所有搜索记录
     * @param pageDTO  分页体
     * @return  搜索记录列表
     */
    Page<SearchRecordDTO> searchRecordPanel(PageDTO pageDTO);

    /**
     * 所有浏览记录
     * @param pageDTO  分页体
     * @return  浏览记录列表
     */
    Page<BrowseRecordDTO> browseRecordPanel(PageDTO pageDTO);

    /**
     * 获取所有用户
     * @return  用户列表
     */
    List<UserDTO> userPanel();

}
