package com.kuaipin.user.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户搜索记录DTO
 * @Author: ljf
 * @DateTime: 2021/12/13 14:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    /**
     * 用户昵称
     */
    private String uNickname;

    private Date createTime;


    @Override
    public String toString() {
        return "SearchRecordDTO{" +
                "searchId='" + searchId + '\'' +
                ", searchKeyword='" + searchKeyword + '\'' +
                ", searchExtend='" + searchExtend + '\'' +
                ", uId='" + uId + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
