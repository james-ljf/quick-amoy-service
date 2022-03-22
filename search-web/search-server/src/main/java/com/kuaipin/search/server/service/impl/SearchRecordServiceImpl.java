package com.kuaipin.search.server.service.impl;

import com.github.pagehelper.page.PageMethod;
import com.kuaipin.common.entity.Page;
import com.kuaipin.common.entity.Response;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.search.api.entity.SearchRecordDTO;
import com.kuaipin.search.server.entity.po.SearchRecord;
import com.kuaipin.search.server.entity.response.SearchHistoryVO;
import com.kuaipin.search.server.repository.SearchRepository;
import com.kuaipin.search.server.service.SearchRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SearchRepository searchRepository;

    @Override
    public Page<SearchRecordDTO> findAllSearchRecord(PageDTO pageDTO) {
        // 获取当前页和每页数量
        int pageNum = pageDTO.getPageNum();
        int pageSize = pageDTO.getPageSize();
        // 物理分页
        PageMethod.startPage(pageNum, pageSize);
        // 查询所有搜索记录并分页
        Page<SearchRecordDTO> result = searchRepository.findAllSearchRecord();
        log.info("[210.findAllSearchRecord page success] : req = {}, result = {}", pageDTO, result);
        if (result != null){
            result.setPageNum(pageNum);
            result.setPageSize(pageSize);
            return result;
        }
        return null;
    }

    @Override
    public Response<List<SearchHistoryVO>> findSearchHistoryByUid(Long uId) {
        log.info("[200.get uId], uId = " + uId);


        return null;
    }

}
