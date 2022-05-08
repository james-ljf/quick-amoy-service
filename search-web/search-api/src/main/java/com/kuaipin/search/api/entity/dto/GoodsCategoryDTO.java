package com.kuaipin.search.api.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ljf
 * @DateTime: 2022/5/7 10:25
 */
@Data
public class GoodsCategoryDTO implements Serializable {

    /**
     * 大品类id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long typeId;

    /**
     * 大品类名称
     */
    private String typeName;

    private List<SmallCategoryDTO> smallCategoryDTOList;

}
