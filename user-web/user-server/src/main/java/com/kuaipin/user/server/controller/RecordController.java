package com.kuaipin.user.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.kuaipin.common.annotation.ApiDescription;
import com.kuaipin.common.constants.PubConstants;
import com.kuaipin.common.entity.Response;
import com.kuaipin.common.entity.dto.Code;
import com.kuaipin.user.server.entity.po.BrowseRecord;
import com.kuaipin.user.server.entity.po.SearchRecord;
import com.kuaipin.user.server.entity.request.BrowseRecordRequest;
import com.kuaipin.user.server.service.RecordService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: ljf
 * @DateTime: 2022/4/28 12:37
 */
@RestController
@RequestMapping(value = {"/using"})
public class RecordController {

    private RecordService recordService;
    @Autowired
    private void setRecordService(RecordService recordService){
        this.recordService = recordService;
    }

    @ApiDescription(desc = "获取搜索记录")
    @PostMapping(value = {"/rel/ser/record"})
    public Response<Object> searchRecordPanel(@RequestBody JSONObject object){
        if (ObjectUtils.isEmpty(object.get(PubConstants.UID))){
            return Response.fail(Code.ERROR_PARAMS);
        }
        long uId = object.getLong(PubConstants.UID);
        List<SearchRecord> resultList = recordService.latelySearchRecord(uId, 7);
        return Response.success(resultList);
    }

    @PostMapping(value = {"/rel/browse/record"})
    public Response<Object> browseRecordPanel(@RequestBody JSONObject object){
        if (ObjectUtils.isEmpty(object.getLong(PubConstants.UID))){
            return Response.fail(Code.ERROR_PARAMS);
        }
        long uId = object.getLong(PubConstants.UID);
        Map<String, List<BrowseRecord>> resultMap = recordService.latelyBrowseRecord(uId);
        return Response.success(resultMap);
    }

    @PostMapping(value = {"/rel/del/ser/record"})
    public Response<Object> delSearchRecord(@RequestBody JSONObject object){
        Long uId = object.getLong(PubConstants.UID);
        Long searchId = object.getLong(PubConstants.SEARCH_ID);
        if (ObjectUtils.isEmpty(uId) || ObjectUtils.isEmpty(searchId)){
            return Response.fail(Code.ERROR_PARAMS);
        }

        return recordService.loseSearchRecord(searchId, uId);
    }

    @PostMapping(value = {"/rel/del/browse/record"})
    public Response<Object> delBrowseRecord(@RequestBody JSONObject object){
        Long uId = object.getLong(PubConstants.UID);
        Long browseId = object.getLong(PubConstants.BROWSE_ID);
        if (ObjectUtils.isEmpty(uId) || ObjectUtils.isEmpty(browseId)){
            return Response.fail(Code.ERROR_PARAMS);
        }
        return recordService.loseBrowseRecord(browseId, uId);
    }

    @PostMapping(value = {"/rel/increase/browse/record"})
    public Response<Object> increaseBrowseRecord(@RequestBody BrowseRecordRequest request){
        if (ObjectUtils.isEmpty(request)){
            return Response.fail(Code.ERROR_PARAMS);
        }
        int num = recordService.increaseBrowseRecord(request);
        return Response.success(num);
    }

}
