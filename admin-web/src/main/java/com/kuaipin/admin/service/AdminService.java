package com.kuaipin.admin.service;

import com.kuaipin.admin.entity.request.AdminRequest;
import com.kuaipin.admin.entity.response.AdminVO;

/**
 * @Author: ljf
 * @DateTime: 2022/5/3 0:06
 */
public interface AdminService {

    /**
     * 管理员登录
     * @param request  登录请求
     * @return  登录结果
     */
    AdminVO adminLogin(AdminRequest request);






}
