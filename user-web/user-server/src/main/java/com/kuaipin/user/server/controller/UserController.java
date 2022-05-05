package com.kuaipin.user.server.controller;

import com.kuaipin.common.annotation.ApiDescription;
import com.kuaipin.common.constants.ErrorEnum;
import com.kuaipin.common.constants.SuccessEnum;
import com.kuaipin.common.entity.Response;
import com.kuaipin.common.entity.dto.Code;
import com.kuaipin.common.util.RedisUtil;
import com.kuaipin.user.server.entity.request.UserRequest;
import com.kuaipin.user.server.entity.response.UserLoginVO;
import com.kuaipin.user.server.entity.response.UserVO;
import com.kuaipin.user.server.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: ljf
 * @DateTime: 2022/4/28 11:27
 */
@RestController
@RequestMapping(value = {"/using"})
public class UserController {

    /**
     * token前置key
     */
    private static final String TOKEN_KEY = "u_id:";

    private UserService userService;
    @Autowired
    private void setUserService(UserService userService){
        this.userService = userService;
    }

    @ApiDescription(desc = "发送邮件")
    @GetMapping(value = {"/email/verify"})
    public Response<Object> sendEmail(@RequestParam("email") String email){
        if (StringUtils.isBlank(email)){
            return Response.fail(Code.ERROR_PARAMS);
        }
        return userService.setEmailVerify(email);
    }

    @ApiDescription(desc = "用户注册")
    @PostMapping(value = {"/kp/reg"})
    public Response<Object> userRegister(@RequestBody UserRequest userRequest){
        if (ObjectUtils.isEmpty(userRequest)){
            return Response.fail(Code.ERROR_PARAMS);
        }
        return userService.userRegister(userRequest);
    }

    @ApiDescription(desc = "用户登录")
    @PostMapping(value = {"/kp/login"})
    public Response<Object> userLogin(@RequestBody UserRequest userRequest){
        if (ObjectUtils.isEmpty(userRequest)){
            return Response.fail(Code.ERROR_PARAMS);
        }
        UserLoginVO userLoginVO = userService.setUserLogin(userRequest);
        if (ObjectUtils.isEmpty(userLoginVO)){
            return Response.fail(Code.RESULT_NULL);
        }
        return Response.success(userLoginVO);
    }

    @ApiDescription(desc = "用户退出登录")
    @GetMapping(value = {"/kp/logout"})
    public Response<Object> userLogout(@RequestParam("uid") Long uId){
        if (ObjectUtils.isEmpty(uId)){
            return Response.fail(Code.ERROR_PARAMS);
        }
        // 删除缓存的token
        RedisUtil.del(TOKEN_KEY + uId);
        return Response.success(SuccessEnum.OPERATION_SUCCESS.getMsg());
    }

    @ApiDescription(desc = "获取用户信息面板")
    @GetMapping(value = {"/kp/user"})
    public Response<Object> userInfoPanel(@RequestParam("uid") Long uId){
        if (ObjectUtils.isEmpty(uId)){
            return Response.fail(Code.ERROR_PARAMS);
        }
        UserVO userVO = userService.userInfoPanel(uId);
        return Response.success(userVO);
    }

    @ApiDescription(desc = "修改用户信息")
    @PostMapping(value = {"/kp/user"})
    public Response<Object> changeUserInfo(@RequestBody UserRequest userRequest){
        if (ObjectUtils.isEmpty(userRequest)){
            return Response.fail(Code.ERROR_PARAMS);
        }
        int num = userService.modifyUserInfo(userRequest);
        if (num == -1){
            return Response.fail(ErrorEnum.DATABASE_ERROR);
        }
        return Response.success(num);
    }

}
