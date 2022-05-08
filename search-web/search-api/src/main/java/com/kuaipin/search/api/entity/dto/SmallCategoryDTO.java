package com.kuaipin.search.api.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: ljf
 * @DateTime: 2022/5/7 10:27
 */
@Data
public class SmallCategoryDTO {

    /**
     * 商品小品类id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long sTypeId;

    /**
     * 商品小品类名称
     */
    private String sTypeName;

    /**
     * 商品大品类id
     */
    private Long bTypeId;

    private Date createTime;

    private Date updateTime;

}
