package com.kuaipin.common.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页请求体
 * @Author: ljf
 * @DateTime: 2021/12/13 15:00
 */
@Data
@AllArgsConstructor
public class PageDTO implements Serializable {

    /** 总记录数 */
    private Long totalCount;

    /** 当前页 */
    private Integer pageNum;

    /** 页面记录大小 */
    private Integer pageSize;

    @Override
    public String toString() {
        return "PageDTO{" +
                "totalCount=" + totalCount +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}