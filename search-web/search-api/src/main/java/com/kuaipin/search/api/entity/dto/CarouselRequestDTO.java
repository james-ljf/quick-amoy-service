package com.kuaipin.search.api.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @Author: ljf
 * @DateTime: 2022/5/7 10:31
 */
@Data
public class CarouselRequestDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long carouselId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long goodsNumber;

    private String goodsName;

    private String goodsPic;

    private Long createTime;

    private Long updateTime;

}
