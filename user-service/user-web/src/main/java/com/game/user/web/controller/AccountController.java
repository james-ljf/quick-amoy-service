package com.game.user.web.controller;

import com.game.common.entity.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户账号功能
 * @author lijf
 */
@Slf4j
@RestController
public class AccountController {

    /**
     * 用户注册账号（邮箱注册）
     */
    @PostMapping("/")
    public Response registerAccount(){
        long start = System.currentTimeMillis();

        log.info("200.账号注册成功，cost={}", System.currentTimeMillis() - start);
        return null;
    }

}
