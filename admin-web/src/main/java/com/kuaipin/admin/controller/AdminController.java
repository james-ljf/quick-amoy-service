package com.kuaipin.admin.controller;

import com.kuaipin.common.annotation.ApiDescription;
import com.kuaipin.common.entity.Response;
import com.kuaipin.common.entity.dto.Code;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.common.util.RedisUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: ljf
 * @DateTime: 2022/5/5 21:45
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @ApiDescription(desc = "获取api请求信息")
    @PostMapping(value = {"/api/panel"})
    public Response<Object> apiPanel(@RequestBody PageDTO pageDTO){
        long start = (long) (pageDTO.getPageNum() - 1) * pageDTO.getPageSize();
        long end = start + pageDTO.getPageSize();
        List<Object> apiInfoList = RedisUtil.getList("api_info", start, end);
        if (CollectionUtils.isEmpty(apiInfoList)){
            return Response.fail(Code.RESULT_NULL);
        }
        return Response.success(apiInfoList);
    }

}
