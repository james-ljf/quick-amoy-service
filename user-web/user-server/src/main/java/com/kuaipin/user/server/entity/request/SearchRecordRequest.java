package com.kuaipin.user.server.entity.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ljf
 * @DateTime: 2022/4/11 12:52
 */
@Data
public class SearchRecordRequest implements Serializable {

    /**
     * 搜索记录id
     */
    private Long searchId;

    /**
     * 用户id
     */
    private Long uId;


}
