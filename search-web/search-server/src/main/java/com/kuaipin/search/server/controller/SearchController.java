package com.kuaipin.search.server.controller;

import com.kuaipin.common.entity.Response;
import com.kuaipin.search.server.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * @Author: ljf
 * @DateTime: 2022/3/22 16:26
 */
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping(value = {"/home/recommend"})
    public Response<Object> goodsRecommendPanel(@RequestParam(value = "uid", required = false) Long uId) throws ExecutionException, InterruptedException {
        return searchService.goodsRecommendPanel(null);
    }

}
