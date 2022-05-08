package com.kuaipin.user.server.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    /**
     * 搜索记录id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long searchId;

    /**
     * 搜索关键字
     */
    private String searchKeyword;

    /**
     * 搜索的扩展筛选项
     */
    private String searchExtend;

    /**
     * 是否有结果
     */
    private String isResult;

    /**
     * 用户id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long uId;

    private Date createTime;

    private Date updateTime;

    @Override
    public String toString() {
        return "SearchRecord{" +
                ", searchId='" + searchId + '\'' +
                ", searchKeyword='" + searchKeyword + '\'' +
                ", searchExtend='" + searchExtend + '\'' +
                ", uId='" + uId + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
