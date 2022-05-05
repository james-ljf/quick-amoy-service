package com.kuaipin.search.server.entity.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * 热推商品VO
 * @Author: ljf
 * @DateTime: 2022/5/5 21:15
 */
@Data
public class CarouselVO implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long carouselId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long goodsNumber;

    private String goodsName;

    private String goodsPic;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @Override
    public String toString() {
        return new StringJoiner(", ", CarouselVO.class.getSimpleName() + "[", "]")
                .add("carouselId=" + carouselId)
                .add("goodsNumber=" + goodsNumber)
                .add("goodsName='" + goodsName + "'")
                .add("goodsPic='" + goodsPic + "'")
                .add("createTime=" + createTime)
                .add("updateTime=" + updateTime)
                .toString();
    }
}
