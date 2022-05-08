package com.kuaipin.admin.service.impl;

import cn.hutool.jwt.JWTUtil;
import com.kuaipin.admin.entity.Admin;
import com.kuaipin.admin.entity.request.AdminRequest;
import com.kuaipin.admin.entity.response.AdminVO;
import com.kuaipin.admin.mapper.AdminMapper;
import com.kuaipin.admin.service.AdminService;
import com.kuaipin.common.util.RedisUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ljf
 * @DateTime: 2022/5/4 16:48
 */
@Service
public class AdminServiceImpl implements AdminService {

    /**
     * token前置key
     */
    private static final String TOKEN_KEY = "u_id_";

    private AdminMapper adminMapper;
    @Autowired
    private void setAdminMapper(AdminMapper adminMapper){
        this.adminMapper = adminMapper;
    }


    @Override
    public AdminVO adminLogin(AdminRequest request) {
        Admin admin = adminMapper.getAdminInfo(request.getAdminAccount());
        if (ObjectUtils.isEmpty(admin)){
            return null;
        }
        if (!admin.getAdminPwd().equals(request.getAdminPwd())){
            return null;
        }
        // 密码正确登录成功，生成token，将用户id存到载荷中
        Map<String, Object> claimsMap = new HashMap<>(2);
        long adminId = admin.getAdminId();
        claimsMap.put("uid", adminId);
        String adminToken = JWTUtil.createToken(claimsMap, "1234".getBytes());
        RedisUtil.set(TOKEN_KEY + admin.getAdminId(), adminToken);
        AdminVO adminVO = new AdminVO();
        adminVO.setAdminId(adminId);
        adminVO.setAdminAccount(admin.getAdminAccount());
        adminVO.setToken(adminToken);
        return adminVO;
    }
}
