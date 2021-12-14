package com.kuaipin.search.server.entity.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户搜索历史VO
 * @Author: ljf
 * @DateTime: 2021/12/13 14:26
 */
@Data
@Accessors(chain = true)
public class SearchHistoryVO implements Serializable {

    /**
     * 搜索记录id
     */
    private String searchId;

    /**
     * 搜索关键字
     */
    private String searchKeyword;

    /**
     * 用户id
     */
    private String uId;

    @Override
    public String toString() {
        return "SearchHistoryVO{" +
                "searchId='" + searchId + '\'' +
                ", searchKeyword='" + searchKeyword + '\'' +
                ", uId='" + uId + '\'' +
                '}';
    }
}
