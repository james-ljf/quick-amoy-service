package com.kuaipin.search.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;

/**
 * 接口请求信息
 * @Author: ljf
 * @DateTime: 2022/5/5 21:38
 */
@Data
public class ApiInfo implements Serializable {

    private Long apiId;

    private String apiMethod;

    private String apiRequest;

    private String apiTime;

    private Date createTime;

    private Date updateTime;

    @Override
    public String toString() {
        return new StringJoiner(", ", ApiInfo.class.getSimpleName() + "[", "]")
                .add("apiId=" + apiId)
                .add("apiContent='" + apiMethod + "'")
                .add("apiRequest='" + apiRequest + "'")
                .add("apiTime='" + apiTime + "'")
                .add("createTime=" + createTime)
                .add("updateTime=" + updateTime)
                .toString();
    }
}
