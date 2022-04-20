package com.kuaipin.user.server.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.kuaipin.common.constants.ErrorEnum;
import com.kuaipin.common.constants.SuccessEnum;
import com.kuaipin.common.entity.Response;
import com.kuaipin.common.util.IdUtils;
import com.kuaipin.common.util.JwtUtils;
import com.kuaipin.common.util.RedisUtil;
import com.kuaipin.user.server.entity.Email;
import com.kuaipin.user.server.entity.po.User;
import com.kuaipin.user.server.entity.request.UserRequest;
import com.kuaipin.user.server.entity.response.UserLoginVO;
import com.kuaipin.user.server.external.EmailSender;
import com.kuaipin.user.server.repository.UserRepository;
import com.kuaipin.user.server.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ljf
 * @DateTime: 2022/4/13 13:56
 */
@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * 用户注册验证码缓存key的统一前缀
     */
    private static final String REG_KEY = "reg_verify:";

    private static final ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(10, 20,
            1L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(20),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSender emailSender;

    @Override
    public Response<Object> setEmailVerify(String email) {
        // 获取缓存中的验证码
        String verify = (String) RedisUtil.get(REG_KEY + email);
        if (!StringUtils.isBlank(verify)){
            // 已存在验证码
            return Response.fail(ErrorEnum.VERIFY_IN);
        }
        // 生成验证码
        String regVerify = String.valueOf(RandomUtils.nextInt(1000, 9999));
        Email emailInfo = new Email();
        emailInfo.setTo(email);
        emailInfo.setTitle("快拼电商搜索系统注册");
        emailInfo.setContent(regVerify);
        emailInfo.setTemplateName("RegisterTemplate.html");
        POOL_EXECUTOR.execute(() -> emailSender.sendHtmlEmail(emailInfo));
        return Response.success(SuccessEnum.VERIFY_SUCCESS);
    }

    @Override
    public Response<Object> userRegister(UserRequest userRequest) {
        // 获取缓存里的验证码
        String cacheVerify = (String) RedisUtil.get(REG_KEY + userRequest.getUEmail());
        if (StringUtils.isBlank(cacheVerify) || !cacheVerify.equals(userRequest.getVerify())){
            // 验证码为空或者验证码错误
            logger.info("[5001.userRegister] : req = {}, errorMsg = {}", userRequest, ErrorEnum.VERIFY_ERROR.getMsg());
            return Response.fail(ErrorEnum.VERIFY_ERROR);
        }
        // 查询当前用户是否已注册
        User inUser = userRepository.findUserInfo(userRequest.getUEmail());
        if (ObjectUtils.isNotEmpty(inUser)){
            // 已注册
            logger.info("[5001.userRegister] : req = {}, errorMsg = {}", userRequest, ErrorEnum.USER_REGISTERED.getMsg());
            return Response.fail(ErrorEnum.USER_REGISTERED);
        }
        // 将用户插入数据库
        User user = new User();
        user.setUId(IdUtils.snowflakeId());
        user.setUEmail(userRequest.getUEmail());
        user.setUPassword(SecureUtil.md5().digestHex16(userRequest.getUPassword()));
        user.setCreateTime(new Date());
        user.setUNickname(user.getUEmail());
        int num = userRepository.setUserInfo(user);
        if (num == 0){
            // 注册失败
            return Response.fail(ErrorEnum.USER_REGISTER_ERROR);
        }
        return Response.success(num);
    }

    @Override
    public UserLoginVO setUserLogin(UserRequest userRequest) {
        // 获取登录用户的信息
        User user = userRepository.findUserInfo(userRequest.getUEmail());
        String password = SecureUtil.md5().digestHex16(userRequest.getUPassword());
        if (!password.equals(user.getUPassword())){
            // 如果密码不一致，登录失败
            logger.info("[5002.setUserLogin] : req = {}, errorMsg = {}", userRequest, ErrorEnum.PWD_ERROR.getMsg());
            return null;
        }
        // 密码正确登录成功，生成token，将用户id存到载荷中
        Map<String, Object> claimsMap = new HashMap<>(2);
        long uId = userRequest.getUId();
        claimsMap.put("uid", uId);
        String userToken = JwtUtils.generateToken(claimsMap);
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setUId(uId);
        userLoginVO.setToken(userToken);
        return userLoginVO;
    }
}
