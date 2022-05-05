package com.kuaipin.gateway.configuration;


import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.kuaipin.common.entity.dto.Code;
import com.kuaipin.common.exception.BizBusinessException;
import com.kuaipin.common.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * 自定义全局拦截器
 * @Author: ljf
 * @DateTime: 2022/4/13 10:00
 */
@Slf4j
@Configuration
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private LoginPathConfig loginPathConfig;
    @Autowired
    private void setLoginPathConfig(LoginPathConfig loginPathConfig){
        this.loginPathConfig = loginPathConfig;
    }

    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private void setRedisTemplate(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

//    private static final AntPathMatcher MATCHER = new AntPathMatcher();

    /**
     * token前置key
     */
    private static final String TOKEN_KEY = "u_id_";


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        log.info("[22222.进入全局拦截器AuthGlobalFilter] ： request = {}", request);
        // 获取header
        HttpHeaders headers = request.getHeaders();
        if (hasFilter(request.getPath().toString())){
            // 校验token是否为空
            String token = headers.getFirst("token");
            if (StringUtils.isBlank(token)) {
                log.error("[4444.token error] : {}", Code.ERROR_TOKEN.getMsg());
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                throw new BizBusinessException(Code.ERROR_TOKEN);
            }
            // 解析token
            JWT jwt = JWTUtil.parseToken(token);
            long uid = (long) jwt.getPayload("uid");
            // 判断该id的token是否在缓存中
            String cacheToken = (String) redisTemplate.opsForValue().get(TOKEN_KEY + uid);
            if (StringUtils.isBlank(cacheToken) || !cacheToken.equals(token)){
                // token已过期
                throw new BizBusinessException(Code.ERROR_TOKEN);
            }
        }
        // 继续向下游执行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }

    /**
     * 登录请求拦截判断
     * @param uri  请求path
     * @return  是否是需要进行登录拦截的path
     */
    public boolean hasFilter(String uri){
        String[] paths = loginPathConfig.getPath();
        for (String pattern : paths) {
            if (uri.contains(pattern)){
                return true;
            }
        }
        return false;
    }

}