package com.kuaipin.user.server.service.impl;

import com.github.pagehelper.page.PageMethod;
import com.kuaipin.common.constants.ErrorEnum;
import com.kuaipin.common.constants.SuccessEnum;
import com.kuaipin.common.entity.Page;
import com.kuaipin.common.entity.Response;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.common.util.IdUtils;
import com.kuaipin.user.api.entity.BrowseRecordDTO;
import com.kuaipin.user.api.entity.SearchRecordDTO;
import com.kuaipin.user.server.entity.po.BrowseRecord;
import com.kuaipin.user.server.entity.po.SearchRecord;
import com.kuaipin.user.server.repository.RecordRepository;
import com.kuaipin.user.server.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
        if (ObjectUtils.isEmpty(uId) || ObjectUtils.isEmpty(size)){
            return new ArrayList<>();
        }
        log.info("[2111.browseRecordByUid serviceImpl] : req = {}, {}", uId, size);
        return recordRepository.findBrowseRecordByUid(uId, size);
    }

    @Override
    public int increaseSearchRecord(SearchRecordDTO searchRecordDTO) {
        // 构建搜索记录实体
        SearchRecord searchRecord = new SearchRecord();
        searchRecord.setSearchId(IdUtils.snowflakeId());
        searchRecord.setSearchKeyword(searchRecordDTO.getSearchKeyword());
        searchRecord.setUId(searchRecordDTO.getUId());
        searchRecord.setCreateTime(new Date());
        searchRecord.setUpdateTime(new Date());
        return recordRepository.setSearchRecord(searchRecord);
    }

    @Override
    public int increaseBrowseRecord(BrowseRecordDTO browseRecordDTO) {
        // 查询浏览记录是否已存在
        BrowseRecord uRecord = recordRepository.findBrowseRecord(browseRecordDTO.getBrowseId(), browseRecordDTO.getUId());
        BrowseRecord browseRecord = new BrowseRecord();
        BeanUtils.copyProperties(browseRecordDTO, browseRecord);
        browseRecord.setUpdateTime(new Date());
        // 对数据库操作的数量
        int num = -1;
        if (ObjectUtils.isEmpty(uRecord)){
            // 没有该浏览记录，则添加到数据库
            num = recordRepository.setBrowseRecord(browseRecord);
        }else{
            // 有记录则更新update_time字段
            num = recordRepository.modifyBrowseRecord(browseRecord);
        }
        return num;
    }

    @Override
    public Response<Object> loseSearchRecord(Long searchId, Long uId) {
        int num = recordRepository.cancelSearchRecord(searchId, uId);
        if (num == 0 || num == -1){
            return Response.fail(ErrorEnum.DATABASE_ERROR);
        }
        return Response.success(SuccessEnum.OPERATION_SUCCESS.getMsg());
    }

    @Override
    public Response<Object> loseBrowseRecord(Long browseId, Long uId) {
        int num = recordRepository.cancelBrowseRecord(browseId, uId);
        if (num == 0 || num == -1){
            return Response.fail(ErrorEnum.DATABASE_ERROR);
        }
        return Response.success(SuccessEnum.OPERATION_SUCCESS.getMsg());
    }

}
