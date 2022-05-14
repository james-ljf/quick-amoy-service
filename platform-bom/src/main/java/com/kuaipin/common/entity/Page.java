package com.kuaipin.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询结果封装
 * @Author: ljf
 * @DateTime: 2021/12/13 14:53
 */
@Data
public class Page<T> implements Serializable {

    /** 总记录数 */
    private Long totalCount;

    /** 当前页 */
    private Integer pageNum ;

    /** 页面记录大小 */
    private Integer pageSize;

    /** 对象列表 */
    private List<T> resultList;

    public Page(){}

    public Page(Long totalCount, List<T> resultList){
        this.totalCount = totalCount;
        this.resultList = resultList;
    }

    public Page(Long totalCount, Integer pageNum, Integer pageSize, List<T> resultList){
        this.totalCount = totalCount;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.resultList = resultList;
    }

}
