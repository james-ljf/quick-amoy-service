package com.kuaipin.admin.controller;

import com.kuaipin.admin.entity.request.AdminRequest;
import com.kuaipin.admin.entity.response.AdminVO;
import com.kuaipin.admin.service.AdminService;
import com.kuaipin.common.annotation.ApiDescription;
import com.kuaipin.common.constants.SuccessEnum;
import com.kuaipin.common.entity.Response;
import com.kuaipin.common.entity.dto.Code;
import com.kuaipin.common.util.RedisUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: ljf
 * @DateTime: 2022/5/5 21:45
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;
    @Autowired
    private void setAdminService(AdminService adminService){
        this.adminService = adminService;
    }

    @ApiDescription(desc = "管理员登录")
    @PostMapping(value = {"/login"})
    public Response<Object> setLoginStatus(@RequestBody AdminRequest request){
        if (ObjectUtils.isEmpty(request)){
            return Response.fail(Code.ERROR_PARAMS);
        }
        AdminVO adminVO = adminService.adminLogin(request);
        return Response.success(adminVO);
    }

    @ApiDescription(desc = "管理员退出登录")
    @GetMapping(value = {"/rel/logout"})
    public Response<Object> adminLogout(@RequestParam("adminId") Long adminId){
        if (ObjectUtils.isEmpty(adminId)){
            return Response.fail(Code.ERROR_PARAMS);
        }
        RedisUtil.del("u_id_" + adminId);
        return Response.success(SuccessEnum.OPERATION_SUCCESS);
    }

}
