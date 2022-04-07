package com.kuaipin.user.server.service.impl;

import com.github.pagehelper.page.PageMethod;
import com.kuaipin.common.entity.Page;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.user.server.entity.po.BrowseRecord;
import com.kuaipin.user.server.entity.po.SearchRecord;
import com.kuaipin.user.server.repository.RecordRepository;
import com.kuaipin.user.server.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索记录接口实现层
 * @Author: ljf
 * @DateTime: 2021/12/13 14:40
 */
@Slf4j
@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Override
    public Page<SearchRecord> allSearchRecord(PageDTO pageDTO) {
        // 获取当前页和每页数量
        int pageNum = pageDTO.getPageNum();
        int pageSize = pageDTO.getPageSize();
        // 物理分页
        PageMethod.startPage(pageNum, pageSize);
        // 查询所有搜索记录并分页
        Page<SearchRecord> result = recordRepository.findAllSearchRecord();
        log.info("[2100.allSearchRecord page success] : req = {}, result = {}", pageDTO, result);
        if (result != null){
            // 结果不为空
            result.setPageNum(pageNum);
            result.setPageSize(pageSize);
            return result;
        }
        return null;
    }

    @Override
    public List<SearchRecord> latelySearchRecord(Long uId, Integer size) {
        if (size == null){
            return new ArrayList<>();
        }
        log.info("[2110.searchHistoryByUid serviceImpl] : req = {}, {}", uId, size);
        return recordRepository.findSearchRecordByTime(uId, size);
    }

    @Override
    public List<BrowseRecord> latelyBrowseRecord(Long uId, Integer size) {
        if (uId == null || size == null){
            return new ArrayList<>();
        }
        log.info("[2111.browseRecordByUid serviceImpl] : req = {}, {}", uId, size);
        return recordRepository.findBrowseRecordByUid(uId, size);
    }

}
