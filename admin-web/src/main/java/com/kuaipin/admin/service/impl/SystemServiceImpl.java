package com.kuaipin.admin.service.impl;

import com.kuaipin.admin.service.SystemService;
import com.kuaipin.common.entity.Page;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.search.api.entity.dto.CarouselDTO;
import com.kuaipin.search.api.entity.dto.CarouselRequestDTO;
import com.kuaipin.search.api.entity.dto.GoodsCategoryDTO;
import com.kuaipin.search.api.entity.dto.GoodsDTO;
import com.kuaipin.search.api.service.GoodsProcessService;
import com.kuaipin.user.api.entity.BrowseRecordDTO;
import com.kuaipin.user.api.entity.SearchRecordDTO;
import com.kuaipin.user.api.entity.UserDTO;
import com.kuaipin.user.api.service.RecordProcessService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ljf
 * @DateTime: 2022/5/7 11:02
 */
@Service
public class SystemServiceImpl implements SystemService {

    @DubboReference
    private GoodsProcessService goodsProcessService;

    @DubboReference
    private RecordProcessService recordProcessService;

    @Override
    public List<GoodsCategoryDTO> goodsCategoryList() {
        return goodsProcessService.goodsCategoryList();
    }

    @Override
    public Page<GoodsDTO> goodsPanel(PageDTO pageDTO) {
        return goodsProcessService.allGoodsPanel(pageDTO);
    }

    @Override
    public int setGoodsCarousel(CarouselRequestDTO requestDTO) {
        return goodsProcessService.setGoodsCarousel(requestDTO);
    }

    @Override
    public int cancelGoodsCarousel(Long carouselId) {
        return goodsProcessService.cancelGoodsCarousel(carouselId);
    }

    @Override
    public List<CarouselDTO> carouselGoodsPanel() {
        return goodsProcessService.carouselGoodsPanel();
    }

    @Override
    public Page<SearchRecordDTO> searchRecordPanel(PageDTO pageDTO) {
        return recordProcessService.allSearchRecord(pageDTO);
    }

    @Override
    public Page<BrowseRecordDTO> browseRecordPanel(PageDTO pageDTO) {
        return recordProcessService.allBrowseRecord(pageDTO);
    }

    @Override
    public List<UserDTO> userPanel() {
        return recordProcessService.getUserList();
    }
}
