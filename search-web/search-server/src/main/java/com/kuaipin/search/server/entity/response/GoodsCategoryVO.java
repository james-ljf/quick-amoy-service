package com.kuaipin.search.server.entity.response;

import com.kuaipin.search.server.entity.po.SmallCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;

/**
 * @Author: ljf
 * @DateTime: 2022/4/20 13:39
 */
@Data
public class GoodsCategoryVO implements Serializable {

    /**
     * 大品类id
     */
    private Long typeId;

    /**
     * 大品类名称
     */
    private String typeName;


    private List<SmallCategory> smallCategoryList;

    @Override
    public String toString() {
        return new StringJoiner(", ", GoodsCategoryVO.class.getSimpleName() + "[", "]")
                .add("typeId=" + typeId)
                .add("typeName='" + typeName + "'")
                .add("smallCategoryList=" + smallCategoryList)
                .toString();
    }
}
