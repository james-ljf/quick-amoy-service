package com.kuaipin.admin.controller;

import com.kuaipin.admin.service.SystemService;
import com.kuaipin.common.annotation.ApiDescription;
import com.kuaipin.common.constants.ErrorEnum;
import com.kuaipin.common.constants.SuccessEnum;
import com.kuaipin.common.entity.Page;
import com.kuaipin.common.entity.Response;
import com.kuaipin.common.entity.dto.Code;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.common.util.RedisUtil;
import com.kuaipin.search.api.entity.dto.CarouselDTO;
import com.kuaipin.search.api.entity.dto.CarouselRequestDTO;
import com.kuaipin.search.api.entity.dto.GoodsCategoryDTO;
import com.kuaipin.search.api.entity.dto.GoodsDTO;
import com.kuaipin.user.api.entity.BrowseRecordDTO;
import com.kuaipin.user.api.entity.SearchRecordDTO;
import com.kuaipin.user.api.entity.UserDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: ljf
 * @DateTime: 2022/5/7 10:59
 */
@RestController
@RequestMapping(value = {"/admin"})
public class SystemController {

    private SystemService systemService;
    @Autowired
    private void setsystemService(SystemService systemService){
        this.systemService = systemService;
    }

    @ApiDescription(desc = "获取api请求信息")
    @PostMapping(value = {"/rel/api/panel"})
    public Response<Object> apiPanel(@RequestBody PageDTO pageDTO){
        if (ObjectUtils.isEmpty(pageDTO)){
            return Response.fail(Code.ERROR_PARAMS);
        }
        long start = (long) (pageDTO.getPageNum() - 1) * pageDTO.getPageSize();
        long end = start + pageDTO.getPageSize();
        List<Object> apiInfoList = RedisUtil.getList("api_info", start, end);
        if (CollectionUtils.isEmpty(apiInfoList)){
            return Response.fail(Code.RESULT_NULL);
        }
        return Response.success(apiInfoList);
    }

    @ApiDescription(desc = "获取api请求信息的数据数量")
    @GetMapping(value = {"/rel/api/many"})
    public Response<Object> apiNum(){
        List<Object> apiInfoList = RedisUtil.getList("api_info", 0, -1);
        if (CollectionUtils.isEmpty(apiInfoList)){
            return Response.success(0);
        }
        int size = apiInfoList.size();
        return Response.success(size);
    }

    @ApiDescription(desc = "获取所有商品品类")
    @GetMapping(value = {"/rel/goods/category/list"})
    public Response<Object> goodsCategoryPanel(){
        List<GoodsCategoryDTO> categoryList = systemService.goodsCategoryList();
        if (CollectionUtils.isEmpty(categoryList)){
            return Response.fail(Code.RESULT_NULL);
        }
        return Response.success(categoryList);
    }

    @ApiDescription(desc = "获取所有商品")
    @PostMapping(value = {"/rel/goods/panel"})
    public Response<Object> goodsPanel(@RequestBody PageDTO pageDTO){
        if (ObjectUtils.isEmpty(pageDTO)){
            return Response.fail(Code.ERROR_PARAMS);
        }
        Page<GoodsDTO> result = systemService.goodsPanel(pageDTO);
        return Response.success(result);
    }

    @ApiDescription(desc = "设置热推商品")
    @PostMapping(value = {"/rel/goods/hot"})
    public Response<Object> setHotGoods(@RequestBody CarouselRequestDTO requestDTO){
        if (ObjectUtils.isEmpty(requestDTO)){
            return Response.fail(Code.ERROR_PARAMS);
        }
        int num = systemService.setGoodsCarousel(requestDTO);
        if (num < 1){
            return Response.fail(ErrorEnum.DATABASE_ERROR);
        }
        return Response.success(Code.SUCCESS);
    }

    @ApiDescription(desc = "删除热推商品")
    @GetMapping(value = {"/rel/del/goods/hot"})
    public Response<Object> delHotGoods(@RequestParam("carousel_id") Long carouselId){
        if (ObjectUtils.isEmpty(carouselId)){
            return Response.fail(Code.ERROR_PARAMS);
        }
        int num = systemService.cancelGoodsCarousel(carouselId);
        if (num < 1){
            return Response.fail(ErrorEnum.DATABASE_ERROR);
        }
        return Response.success(Code.SUCCESS);
    }

    @ApiDescription(desc = "获取热推商品列表")
    @GetMapping(value = {"/rel/goods/hot"})
    public Response<Object> hotGoodsPanel(){
        List<CarouselDTO> results = systemService.carouselGoodsPanel();
        return Response.success(results);
    }
    
    @ApiDescription(desc = "获取搜索记录列表")
    @PostMapping(value = {"/rel/record/search"})
    public Response<Object> searchRecordPanel(@RequestBody PageDTO pageDTO){
        if (ObjectUtils.isEmpty(pageDTO)){
            return Response.fail(Code.ERROR_PARAMS);
        }
        Page<SearchRecordDTO> resultsPage = systemService.searchRecordPanel(pageDTO);
        return Response.success(resultsPage);
    }

    @ApiDescription(desc = "获取浏览记录列表")
    @PostMapping(value = {"/rel/record/browse"})
    public Response<Object> browseRecordPanel(@RequestBody PageDTO pageDTO){
        if (ObjectUtils.isEmpty(pageDTO)){
            return Response.fail(Code.ERROR_PARAMS);
        }
        Page<BrowseRecordDTO> resultsPage = systemService.browseRecordPanel(pageDTO);
        return Response.success(resultsPage);
    }

    @ApiDescription(desc = "获取所有用户")
    @GetMapping(value = {"/rel/user"})
    public Response<Object> userInfoPanel(){
        List<UserDTO> userDTOList = systemService.userPanel();
        return Response.success(userDTOList);
    }

}
