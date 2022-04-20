package com.kuaipin.search.server.service;

import com.kuaipin.search.server.entity.response.GoodsCategoryVO;

import java.util.List;

/**
 * 商品品类操作
 * @Author: ljf
 * @DateTime: 2022/4/20 13:38
 */
public interface CategoryService {

    /**
     * 获取商品品类面板
     * @return  商品品类列表
     */
    List<GoodsCategoryVO> getGoodsCategoryList();

}
