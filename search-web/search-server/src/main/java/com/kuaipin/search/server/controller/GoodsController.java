package com.kuaipin.search.server.controller;

import com.kuaipin.common.annotation.ApiDescription;
import com.kuaipin.common.constants.ErrorEnum;
import com.kuaipin.common.constants.SuccessEnum;
import com.kuaipin.common.entity.Page;
import com.kuaipin.common.entity.Response;
import com.kuaipin.common.entity.dto.Code;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.search.api.entity.dto.CarouselRequestDTO;
import com.kuaipin.search.server.entity.po.Carousel;
import com.kuaipin.search.server.entity.response.CarouselVO;
import com.kuaipin.search.server.entity.response.GoodsCategoryVO;
import com.kuaipin.search.server.entity.response.GoodsInfoVO;
import com.kuaipin.search.server.service.GoodsService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: ljf
 * @DateTime: 2022/4/20 14:30
 */
@RestController
@RequestMapping(value = {"/pin"})
public class GoodsController {

    private GoodsService goodsService;
    @Autowired
    private void setGoodsService(GoodsService goodsService){
        this.goodsService = goodsService;
    }

    @ApiDescription(desc = "查询所有商品品类")
    @GetMapping(value = {"/goods/category/list"})
    public Response<Object> goodsCategoryPanel(){
        List<GoodsCategoryVO> categoryVOList = goodsService.getGoodsCategoryList();
        if (CollectionUtils.isEmpty(categoryVOList)){
            return Response.fail(Code.RESULT_NULL);
        }
        return Response.success(categoryVOList);
    }

//    @ApiDescription(desc = "获取所有商品")
//    @PostMapping(value = {"/goods/panel"})
//    public Response<Object> goodsPanel(@RequestBody PageDTO pageDTO){
//        if (ObjectUtils.isEmpty(pageDTO)){
//            return Response.fail(Code.ERROR_PARAMS);
//        }
//        Page<GoodsInfoVO> result = goodsService.getGoodsPanel(pageDTO);
//        return Response.success(result);
//    }
//
//    @ApiDescription(desc = "设置热推商品")
//    @PostMapping(value = {"/goods/hot"})
//    public Response<Object> setHotGoods(@RequestBody CarouselRequestDTO requestDTO){
//        if (ObjectUtils.isEmpty(requestDTO)){
//            return Response.fail(Code.ERROR_PARAMS);
//        }
//        int num = goodsService.setGoodsCarousel(requestDTO);
//        if (num < 1){
//            return Response.fail(ErrorEnum.DATABASE_ERROR);
//        }
//        return Response.success(SuccessEnum.OPERATION_SUCCESS);
//    }
//
//    @ApiDescription(desc = "删除热推商品")
//    @GetMapping(value = {"/del/goods/hot"})
//    public Response<Object> delHotGoods(@RequestParam("carousel_id") Long carouselId){
//        if (ObjectUtils.isEmpty(carouselId)){
//            return Response.fail(Code.ERROR_PARAMS);
//        }
//        int num = goodsService.cancelGoodsCarousel(carouselId);
//        if (num < 1){
//            return Response.fail(ErrorEnum.DATABASE_ERROR);
//        }
//        return Response.success(SuccessEnum.OPERATION_SUCCESS);
//    }
//
//    @ApiDescription(desc = "获取热推商品列表")
//    @GetMapping(value = {"/goods/hot"})
//    public Response<Object> hotGoodsPanel(){
//        List<CarouselVO> results = goodsService.carouselGoodsPanel();
//        return Response.success(results);
//    }

}
