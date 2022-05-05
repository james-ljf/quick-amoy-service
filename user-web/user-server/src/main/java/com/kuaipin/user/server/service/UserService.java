package com.kuaipin.user.server.service;

import com.kuaipin.common.entity.Response;
import com.kuaipin.user.server.entity.request.UserRequest;
import com.kuaipin.user.server.entity.response.UserLoginVO;
import com.kuaipin.user.server.entity.response.UserVO;

/**
 * 用户操作接口
 * @Author: ljf
 * @DateTime: 2022/4/13 13:42
 */
public interface UserService {

    /**
     * 发送验证码
     * @param email 邮箱号
     * @return 发送结果
     */
    Response<Object> setEmailVerify(String email);

    /**
     * 用户注册
     * @param userRequest  用户信息
     * @return  注册结果
     */
    Response<Object> userRegister(UserRequest userRequest);

    /**
     * 用户登录
     * @param userRequest 用户信息
     * @return  登录结果
     */
    UserLoginVO setUserLogin(UserRequest userRequest);

    /**
     * 获取用户信息
     * @param uId  用户id
     * @return  用户信息
     */
    UserVO userInfoPanel(Long uId);

    /**
     * 修改用户信息
     * @param userRequest  新的用户信息
     * @return  修改数量
     */
    int modifyUserInfo(UserRequest userRequest);

}
