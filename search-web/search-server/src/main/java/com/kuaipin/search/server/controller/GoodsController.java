package com.kuaipin.search.server.controller;

import com.kuaipin.common.annotation.ApiDescription;
import com.kuaipin.common.entity.Response;
import com.kuaipin.common.entity.dto.Code;
import com.kuaipin.search.server.entity.response.GoodsCategoryVO;
import com.kuaipin.search.server.service.GoodsService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: ljf
 * @DateTime: 2022/4/20 14:30
 */
@RestController
@RequestMapping(value = {"/pin"})
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @ApiDescription(desc = "查询所有商品品类")
    @GetMapping(value = {"/goods/category/list"})
    public Response<Object> goodsCategoryPanel(){
        List<GoodsCategoryVO> categoryVOList = goodsService.getGoodsCategoryList();
        if (CollectionUtils.isEmpty(categoryVOList)){
            return Response.fail(Code.RESULT_NULL);
        }
        return Response.success(categoryVOList);
    }

}
