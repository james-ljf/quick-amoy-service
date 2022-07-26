package com.kuaipin.search.server.service;

import com.kuaipin.common.entity.Response;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.search.server.entity.request.CategoryRequest;

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
    Response<Object> goodsRecommendPanel(Long uid);

    /**
     * 搜索联想
     * @param keyword   搜索框输入的关键词
     * @return  推荐词
     */
    Response<Object> searchAssociation(String keyword);

    /**
     * 按照商品小品类筛选搜索
     * @param typeName  商品小品类名称
     * @param pageDTO   分页请求体
     * @return  商品列表
     */
    Response<Object> searchCategoryPanel(String typeName, PageDTO pageDTO);

    /**
     * 输入的关键词分页搜索商品数据
     * @param keyword   关键词
     * @param uId   用户id
     * @param pageDTO   分页请求体
     * @param type  一个添加搜索记录的判断
     * @return  商品列表
     */
    Response<Object> searchKeywordPanel(String keyword, Long uId, PageDTO pageDTO, String type);

    /**
     * 搜索发现
     * @param uid 用户id
     * @return  搜索发现词语列表
     */
    Response<Object> searchDiscovery(Long uid);

    /**
     * 搜索商品信息
     * @param goodsNumber  商品编号
     * @return  商品信息
     */
    Response<Object> searchGoods(String goodsNumber);

}
