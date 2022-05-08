package com.kuaipin.search.server.entity.request;

import lombok.Data;

import java.util.StringJoiner;

/**
 * 商品品类请求体
 * @Author: ljf
 * @DateTime: 2022/4/6 13:27
 */
@Data
public class CategoryRequest {

    /**
     * 商品小品类id
     */
    private Long sTypeId;

    /**
     * 商品品类名称
     */
    private String sTypeName;

    @Override
    public String toString() {
        return new StringJoiner(", ", CategoryRequest.class.getSimpleName() + "[", "]")
                .add("sTypeId=" + sTypeId)
                .add("sTypeName='" + sTypeName + "'")
                .toString();
    }
}
