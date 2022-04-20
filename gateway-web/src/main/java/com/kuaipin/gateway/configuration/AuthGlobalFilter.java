package com.kuaipin.gateway.configuration;

import com.kuaipin.common.entity.dto.Code;
import com.kuaipin.common.exception.BizBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 自定义全局拦截器
 * @Author: ljf
 * @DateTime: 2022/4/13 10:00
 */
@Slf4j
@Configuration
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        log.info("[22222.进入全局拦截器AuthGlobalFilter] ： request = {}", request);
        // 获取header
        HttpHeaders headers = request.getHeaders();
        // 校验token是否为空
        List<String> tokens = headers.get("token");
        if (CollectionUtils.isEmpty(tokens)) {
            log.error("[4444.token error] : {}", Code.ERROR_TOKEN.getMsg());
            ServerHttpResponse response = exchange.getResponse();
//            byte[] bits = Code.ERROR_TOKEN.toString().getBytes(StandardCharsets.UTF_8);
//            DataBuffer buffer = response.bufferFactory().wrap(bits);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            throw new BizBusinessException(Code.ERROR_TOKEN);
            //return response.writeWith(Mono.just(buffer));
        }
        // 继续向下游执行
        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return 1;
    }


}