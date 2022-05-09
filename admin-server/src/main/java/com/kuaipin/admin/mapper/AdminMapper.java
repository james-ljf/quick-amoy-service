package com.kuaipin.admin.mapper;

import com.kuaipin.admin.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: ljf
 * @DateTime: 2022/5/4 16:48
 */
@Mapper
public interface AdminMapper {

    /**
     * 获取管理员信息
     * @param account  账号
     * @return  管理员信息
     */
    Admin getAdminInfo(String account);

}
