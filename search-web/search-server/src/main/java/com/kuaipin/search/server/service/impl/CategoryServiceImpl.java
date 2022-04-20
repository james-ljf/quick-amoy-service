package com.kuaipin.search.server.service.impl;

import com.kuaipin.search.server.entity.response.GoodsCategoryVO;
import com.kuaipin.search.server.mapper.GoodsMapper;
import com.kuaipin.search.server.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ljf
 * @DateTime: 2022/4/20 13:54
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsCategoryVO> getGoodsCategoryList() {
        return goodsMapper.getGoodsCategory();
    }

}
