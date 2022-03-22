package com.kuaipin.search.server.service;

import com.kuaipin.common.entity.Response;

import java.util.concurrent.ExecutionException;

/**
 * 搜索系列接口层
 * @Author: ljf
 * @DateTime: 2022/3/22 14:30
 */
public interface SearchService {

    /**
     * 获取商品推荐列表
     * @param uid   用户id
     * @return  推荐列表
     */
    Response<Object> goodsRecommendPanel(Long uid) throws ExecutionException, InterruptedException;

}
