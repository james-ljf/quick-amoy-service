package com.kuaipin.search.api.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: ljf
 * @DateTime: 2022/5/7 10:34
 */
@Data
public class CarouselDTO implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long carouselId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long goodsNumber;

    private String goodsName;

    private String goodsPic;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
