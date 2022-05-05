package com.kuaipin.search.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.kuaipin.common.annotation.Description;
import com.kuaipin.common.entity.Response;
import com.kuaipin.common.entity.dto.Code;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.search.server.annotation.SystemTime;
import com.kuaipin.search.server.service.SearchService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: ljf
 * @DateTime: 2022/3/22 16:26
 */
@SystemTime
@RestController
@RequestMapping(value = {"/pin"})
public class SearchController {

    private SearchService searchService;
    @Autowired
    private void setSearchService(SearchService searchService){
        this.searchService = searchService;
    }

    @Description(value = "获取商品推荐列表")
    @GetMapping(value = {"/panel/recommend"})
    public Response<Object> goodsRecommendPanel(@RequestParam(value = "uid", required = false) Long uId) {
        return searchService.goodsRecommendPanel(null);
    }

    @Description(value = "搜索联想")
    @PostMapping(value = {"/panel/affix"})
    public Response<Object> searchAssociationList(@RequestBody JSONObject object){
        String keyword = object.getString("keyword");
        if (StringUtils.isBlank(keyword)){
            return Response.fail(Code.ERROR_PARAMS);
        }
        return searchService.searchAssociation(keyword);
    }

    @Description(value = "按照商品小品类筛选搜索")
    @PostMapping(value = {"/goods/category"})
    public Response<Object> categoryGoodsPanel(@RequestBody JSONObject object){
        String typeName = object.getString("typeName");
        if (StringUtils.isBlank(typeName)){
            return Response.fail(Code.ERROR_PARAMS);
        }
        // 获取分页请求值
        int pageNum = object.getInteger("pageNum");
        int pageSize = object.getInteger("pageSize");
        long total = object.getLong("total");
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPageNum(pageNum);
        pageDTO.setPageSize(pageSize);
        pageDTO.setTotalCount(total);
        if (ObjectUtils.isEmpty(pageDTO)){
            return Response.fail(Code.ERROR_PARAMS);
        }
        return searchService.searchCategoryPanel(typeName, pageDTO);
    }

    @Description(value = "关键词搜索商品")
    @PostMapping(value = {"/goods/keyword"})
    public Response<Object> hasKeywordGoodsPanel(@RequestBody JSONObject object){
        String keyword = object.getString("typeName");
        if (StringUtils.isBlank(keyword)){
            return Response.fail(Code.ERROR_PARAMS);
        }
        Long uId = object.getLong("uid");
        String type = object.getString("type");
        // 获取分页请求值
        int pageNum = object.getInteger("pageNum");
        int pageSize = object.getInteger("pageSize");
        long total = object.getLong("total");
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPageNum(pageNum);
        pageDTO.setPageSize(pageSize);
        pageDTO.setTotalCount(total);
        if (ObjectUtils.isEmpty(pageDTO)){
            return Response.fail(Code.ERROR_PARAMS);
        }
        return searchService.searchKeywordPanel(keyword, uId, pageDTO, type);
    }

    @Description(value = "搜索发现")
    @GetMapping(value = {"/keyword/list"})
    public Response<Object> discoveryPanel(@RequestParam(value = "uid", required = false) Long uid){
        return searchService.searchDiscovery(uid);
    }

}
