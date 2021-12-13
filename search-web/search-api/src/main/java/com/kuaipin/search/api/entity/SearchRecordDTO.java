package com.kuaipin.search.api.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: ljf
 * @DateTime: 2021/12/13 14:48
 */
@Data
@Accessors(chain = true)
public class SearchRecordDTO implements Serializable {

    /**
     * 搜索记录id
     */
    private String searchId;

    /**
     * 搜索关键字
     */
    private String searchKeyword;

    /**
     * 搜索的扩展筛选项
     */
    private String searchExtend;

    /**
     * 用户id
     */
    private String uId;

    private Date createTime;

    private Date updateTime;

}
