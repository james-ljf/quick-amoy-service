package com.kuaipin.search.server.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @Author: ljf
 * @DateTime: 2022/5/3 21:45
 */
@Data
public class Carousel implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long carouselId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long goodsNumber;

    private String goodsName;

    private String goodsPic;

    private Long createTime;

    private Long updateTime;

    @Override
    public String toString() {
        return new StringJoiner(", ", Carousel.class.getSimpleName() + "[", "]")
                .add("carouselId=" + carouselId)
                .add("goodsNumber=" + goodsNumber)
                .add("goodsName='" + goodsName + "'")
                .add("goodsPic='" + goodsPic + "'")
                .add("createTime='" + createTime + "'")
                .add("updateTime='" + updateTime + "'")
                .toString();
    }
}
