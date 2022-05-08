package com.kuaipin.search.server.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.kuaipin.common.constants.ErrorEnum;
import com.kuaipin.common.entity.Page;
import com.kuaipin.common.entity.Response;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.common.util.IdUtils;
import com.kuaipin.search.api.entity.dto.CarouselRequestDTO;
import com.kuaipin.search.server.convert.EntityCreation;
import com.kuaipin.search.server.entity.po.Carousel;
import com.kuaipin.search.server.entity.po.SmallCategory;
import com.kuaipin.search.server.entity.response.CarouselVO;
import com.kuaipin.search.server.entity.response.GoodsCategoryVO;
import com.kuaipin.search.server.entity.response.GoodsInfoVO;
import com.kuaipin.search.server.mapper.GoodsMapper;
import com.kuaipin.search.server.service.GoodsService;
import java.util.Collections;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: ljf
 * @DateTime: 2022/4/20 13:54
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    private GoodsMapper goodsMapper;
    @Autowired
    private void setGoodsMapper(GoodsMapper goodsMapper){
        this.goodsMapper = goodsMapper;
    }

    private EntityCreation entityCreation;
    @Autowired
    private void setEntityCreation(EntityCreation entityCreation){
        this.entityCreation = entityCreation;
    }

    @Override
    public List<GoodsCategoryVO> getGoodsCategoryList() {
        return goodsMapper.getGoodsCategory();
    }

    @Override
    public int increaseSmallCategory(SmallCategory smallCategory) {
        smallCategory.setCreateTime(new Date());
        smallCategory.setUpdateTime(new Date());
        return goodsMapper.insertSmallCategory(smallCategory);
    }

    @Override
    public Page<GoodsInfoVO> getGoodsPanel(PageDTO pageDTO) {
        int pageNum = pageDTO.getPageNum();
        int pageSize = pageDTO.getPageSize();
        PageMethod.startPage(pageNum, pageSize);
        List<GoodsInfoVO> goodsInfoVOList = goodsMapper.findGoodsInfo();
        if (CollectionUtils.isEmpty(goodsInfoVOList)){
            return new Page<>();
        }
        // 获取分页结果信息
        PageInfo<GoodsInfoVO> pageInfo = new PageInfo<>(goodsInfoVOList);
        Page<GoodsInfoVO> result = new Page<>(pageInfo.getTotal(), goodsInfoVOList);
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        return result;
    }

    @Override
    public int setGoodsCarousel(CarouselRequestDTO requestDTO) {
        Carousel carouselGoods = goodsMapper.findCarouselGoods(requestDTO.getGoodsNumber());
        if (ObjectUtils.isEmpty(carouselGoods)){
            Carousel carousel = new Carousel();
            BeanUtils.copyProperties(requestDTO, carousel);
            carousel.setCarouselId(IdUtils.snowflakeId());
            carousel.setCreateTime(System.currentTimeMillis());
            carousel.setUpdateTime(System.currentTimeMillis());
            return goodsMapper.insertCarouselGoods(carousel);
        }
        return -1;
    }

    @Override
    public int cancelGoodsCarousel(Long carouselId) {
        return goodsMapper.delCarouselGoods(carouselId);
    }

    @Override
    public List<CarouselVO> carouselGoodsPanel() {
        List<Carousel> carousels = goodsMapper.getCarouselGoods();
        if (CollectionUtils.isEmpty(carousels)){
            return Collections.emptyList();
        }
        return carousels.stream().map(obj -> entityCreation.carouselConvertVO(obj)).collect(Collectors.toList());
    }

}
