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
import com.kuaipin.user.server.entity.request.BrowseRecordRequest;
import com.kuaipin.user.server.repository.RecordRepository;
import com.kuaipin.user.server.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 搜索记录接口实现层
 * @Author: ljf
 * @DateTime: 2021/12/13 14:40
 */
@Slf4j
@Service
public class RecordServiceImpl implements RecordService {

    private RecordRepository recordRepository;
    @Autowired
    private void setRecordRepository(RecordRepository recordRepository){
        this.recordRepository = recordRepository;
    }
    private static final String NEAR_KEY = "near_list";
    private static final String LATER_KEY = "later_key";

    @Override
    public Page<SearchRecord> allSearchRecord(Long uId, PageDTO pageDTO) {
        // 获取当前页和每页数量
        int pageNum = pageDTO.getPageNum();
        int pageSize = pageDTO.getPageSize();
        // 物理分页
        PageMethod.startPage(pageNum, pageSize);
        // 查询所有搜索记录并分页
        Page<SearchRecord> result = recordRepository.getAllSearchRecord(uId);
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
        return recordRepository.getSearchRecordByTime(uId, size);
    }

    @Override
    public Page<BrowseRecord> allBrowseRecord(Long uId, PageDTO pageDTO) {
        int pageNum = pageDTO.getPageNum();
        int pageSize = pageDTO.getPageSize();
        PageMethod.startPage(pageNum, pageSize);
        Page<BrowseRecord> result = recordRepository.getAllBrowseRecord(uId);
        log.info("[2100.allBrowseRecord page success] : req = {}, result = {}", pageDTO, result);
        if (result != null){
            // 结果不为空
            result.setPageNum(pageNum);
            result.setPageSize(pageSize);
            return result;
        }
        return null;
    }

    @Override
    public List<BrowseRecord> latelyBrowseRecord(Long uId, Integer size) {
        if (ObjectUtils.isEmpty(uId) || ObjectUtils.isEmpty(size)){
            return new ArrayList<>();
        }
        log.info("[2111.browseRecordByUid serviceImpl] : req = {}, {}", uId, size);
        return recordRepository.getBrowseRecordByUid(uId, size);
    }

    @Override
    public Map<String, List<BrowseRecord>> latelyBrowseRecord(Long uId) {
        // 获取近期和更早之前的浏览记录
        List<BrowseRecord> nearList = recordRepository.findBrowseRecordInNear(uId);
        List<BrowseRecord> laterList = recordRepository.findBrowseRecordInLater(uId);
        Map<String, List<BrowseRecord>> listMap = new HashMap<>(3);
        listMap.put(NEAR_KEY, nearList);
        listMap.put(LATER_KEY, laterList);
        return listMap;
    }

    @Override
    public int increaseSearchRecord(SearchRecordDTO searchRecordDTO) {
        String keyword = searchRecordDTO.getSearchKeyword();
        Long uId = searchRecordDTO.getUId();
        SearchRecord oldRecord = recordRepository.getSearchRecord(keyword, uId);
        if (ObjectUtils.isNotEmpty(oldRecord)){
            oldRecord.setUpdateTime(new Date());
            return recordRepository.modifySearchRecord(oldRecord);
        }
        // 构建搜索记录实体
        SearchRecord searchRecord = new SearchRecord();
        searchRecord.setSearchId(IdUtils.snowflakeId());
        searchRecord.setSearchKeyword(keyword);
        searchRecord.setIsResult(searchRecordDTO.getIsResult());
        searchRecord.setUId(uId);
        searchRecord.setCreateTime(new Date());
        searchRecord.setUpdateTime(new Date());
        return recordRepository.setSearchRecord(searchRecord);
    }

    @Override
    public int increaseBrowseRecord(BrowseRecordRequest request) {
        // 查询浏览记录是否已存在
        BrowseRecord uRecord = recordRepository.getBrowseRecord(request.getGoodsNumber(), request.getUId());
        // 对数据库操作的数量
        int num;
        if (ObjectUtils.isEmpty(uRecord)){
            // 没有该浏览记录，则添加到数据库
            BrowseRecord browseRecord = new BrowseRecord();
            // bean复制属性
            BeanUtils.copyProperties(request, browseRecord);
            browseRecord.setUpdateTime(new Date());
            browseRecord.setCreateTime(new Date());
            num = recordRepository.setBrowseRecord(browseRecord);
        }else{
            // 有记录则更新update_time字段
            uRecord.setCreateTime(new Date());
            num = recordRepository.modifyBrowseRecord(uRecord);
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
