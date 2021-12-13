package com.kuaipin.search.api.service;

import com.kuaipin.common.entity.Page;
import com.kuaipin.common.entity.Response;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.search.api.entity.SearchRecordDTO;

/**
 * @Author: ljf
 * @DateTime: 2021/12/13 14:50
 */
public interface SearchRecordProcessService {

    /**
     * 查询所有搜索记录
     * @param pageDTO   封装的分页请求
     * @return  搜索记录列表
     */
    Response<Page<SearchRecordDTO>> findAllSearchRecord(PageDTO pageDTO);

}
