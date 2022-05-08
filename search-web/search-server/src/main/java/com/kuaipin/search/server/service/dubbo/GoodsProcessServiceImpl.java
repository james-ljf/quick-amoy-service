package com.kuaipin.search.server.service.dubbo;

import com.kuaipin.common.entity.Page;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.common.util.ObjectUtil;
import com.kuaipin.search.api.entity.dto.CarouselDTO;
import com.kuaipin.search.api.entity.dto.CarouselRequestDTO;
import com.kuaipin.search.api.entity.dto.GoodsCategoryDTO;
import com.kuaipin.search.api.entity.dto.GoodsDTO;
import com.kuaipin.search.api.service.GoodsProcessService;
import com.kuaipin.search.server.convert.EntityCreation;
import com.kuaipin.search.server.entity.response.CarouselVO;
import com.kuaipin.search.server.entity.response.GoodsCategoryVO;
import com.kuaipin.search.server.entity.response.GoodsInfoVO;
import com.kuaipin.search.server.service.GoodsService;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: ljf
 * @DateTime: 2022/5/7 10:35
 */
@DubboService
public class GoodsProcessServiceImpl implements GoodsProcessService {

    private final Logger log = LoggerFactory.getLogger(GoodsProcessServiceImpl.class);

    private GoodsService goodsService;
    @Autowired
    private void setGoodsService(GoodsService goodsService){
        this.goodsService = goodsService;
    }

    private EntityCreation entityCreation;
    @Autowired
    private void setEntityCreation(EntityCreation entityCreation){
        this.entityCreation = entityCreation;
    }

    @Override
    public List<GoodsCategoryDTO> goodsCategoryList() {
        List<GoodsCategoryVO> categoryList = goodsService.getGoodsCategoryList();
        List<GoodsCategoryDTO> results = categoryList.stream().map(obj -> entityCreation.categoryConvert(obj)).collect(Collectors.toList());
        log.info("[7001.goodsCategoryList dubbo api] : result = {}", results);
        return results;
    }

    @Override
    public Page<GoodsDTO> allGoodsPanel(PageDTO pageDTO) {
        Page<GoodsInfoVO> goodsInfoPage = goodsService.getGoodsPanel(pageDTO);
        List<GoodsDTO> goodsDTOList = ObjectUtil.copyList(goodsInfoPage.getResultList(), GoodsDTO::new);
        Page<GoodsDTO> resultsPage = new Page<>(
                goodsInfoPage.getTotalCount(),
                goodsInfoPage.getPageNum(),
                goodsInfoPage.getPageSize(),
                goodsDTOList
        );
        log.info("[7002.allGoodsPanel dubbo api] : req = {}, res = {}", pageDTO, resultsPage);
        return resultsPage;
    }

    @Override
    public int setGoodsCarousel(CarouselRequestDTO requestDTO) {
        return goodsService.setGoodsCarousel(requestDTO);
    }

    @Override
    public int cancelGoodsCarousel(Long carouselId) {
        return goodsService.cancelGoodsCarousel(carouselId);
    }

    @Override
    public List<CarouselDTO> carouselGoodsPanel() {
        List<CarouselVO> carouselVOList = goodsService.carouselGoodsPanel();
        List<CarouselDTO> resultsList = ObjectUtil.copyList(carouselVOList, CarouselDTO::new);
        log.info("[7005.carouselGoodsPanel dubbo api] : res = {}", resultsList);
        return resultsList;
    }

}
