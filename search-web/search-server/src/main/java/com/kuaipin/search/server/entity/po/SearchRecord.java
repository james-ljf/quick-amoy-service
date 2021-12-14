package com.kuaipin.search.server.entity.po;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户搜索记录
 * @Author: ljf
 * @DateTime: 2021/12/13 14:14
 */
@Data
@Accessors(chain = true)
public class SearchRecord implements Serializable {

    private Integer id;

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

    @Override
    public String toString() {
        return "SearchRecord{" +
                "id=" + id +
                ", searchId='" + searchId + '\'' +
                ", searchKeyword='" + searchKeyword + '\'' +
                ", searchExtend='" + searchExtend + '\'' +
                ", uId='" + uId + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
