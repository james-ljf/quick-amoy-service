package com.kuaipin.search.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.kuaipin.common.annotation.SystemTime;
import com.kuaipin.common.entity.Response;
import com.kuaipin.search.server.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

/**
 * @Author: ljf
 * @DateTime: 2022/3/22 16:26
 */
@SystemTime
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping(value = {"/home/recommend"})
    public Response<Object> goodsRecommendPanel(@RequestParam(value = "uid", required = false) Long uId) {
        return searchService.goodsRecommendPanel(null);
    }

    @PostMapping(value = {"/key/association"})
    public Response<Object> searchAssociation(@RequestBody JSONObject object){
        String keyword = object.getString("keyword");
        return searchService.searchAssociation(keyword);
    }

}
