package com.kuaipin.search.server.service;

import com.kuaipin.common.entity.Page;
import com.kuaipin.common.entity.Response;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.search.server.entity.po.SearchRecord;
import com.kuaipin.search.server.entity.response.SearchHistoryVO;

import java.util.List;

/**
 * 对搜索记录的操作接口
 * @Author: ljf
 * @DateTime: 2021/12/13 14:24
 */
public interface SearchRecordService {

    /**
     * 查询所有搜索记录
     * @param pageDTO   分页请求体
     * @return  搜索记录分页列表
     */
    Response<Page<SearchRecord>> findAllSearchRecord(PageDTO pageDTO);

    /**
     * 用于查询用户的搜索历史（固定7条）
     * @param uId   用户id
     * @return  用户搜索历史列表
     */
    Response<List<SearchHistoryVO>> findSearchHistoryByUid(Long uId);

}
