package com.game.user.web.service.impl;


import com.game.user.web.service.UserService;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * 用户功能业务实现
 * @author lijf
 */
@Service
public class UserServiceImpl implements UserService {

    private static ThreadPoolExecutor execThreadpool = new ThreadPoolExecutor(50, 300,
            1L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );


}
