package com.kuaipin.search.server.service.impl;

import com.kuaipin.common.entity.Page;
import com.kuaipin.common.entity.Response;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.search.server.entity.po.SearchRecord;
import com.kuaipin.search.server.entity.response.SearchHistoryVO;
import com.kuaipin.search.server.service.SearchRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 搜索记录接口实现层
 * @Author: ljf
 * @DateTime: 2021/12/13 14:40
 */
@Slf4j
@Service
public class SearchRecordServiceImpl implements SearchRecordService {

    @Override
    public Response<Page<SearchRecord>> findAllSearchRecord(PageDTO pageDTO) {
        return null;
    }

    @Override
    public Response<List<SearchHistoryVO>> findSearchHistoryByUid(Long uId) {
        log.info("[200.get uId], uId = " + uId);


        return null;
    }

}
