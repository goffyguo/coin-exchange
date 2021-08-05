package com.guofei.filter;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/06/30/19:37
 * @Description:
 */
@Component
@Slf4j
public class JwtCheckFilter implements GlobalFilter, Ordered {

    @Value("${no.token.access.urls:/admin/login,/admin/validate/code}")
    private Set<String> noTokenAccessUrls;

    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 该过滤器拦截到用户请求处理
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1、不需要token就可以访问
        if (!allowNoTokenAccess(exchange)){
            // 不需要token,直接放行
            return chain.filter(exchange);
        }
        // 2、获取用户的token
        String token = getUserToken(exchange);

        // 3、判断token以否有效
        Boolean aBoolean = redisTemplate.hasKey(token);
        log.info("--redisTemplate--",redisTemplate);
        //Boolean aBoolean = true;
        if (aBoolean!=null && aBoolean){
            return chain.filter(exchange);
        }
        // 返回错误或者是一个没有授权的结果
        return buildNoAuthorizationResult(exchange);
    }

    /**
     * 给用户响应一个没有token的错误
     * @param exchange
     * @return
     */
    private Mono<Void> buildNoAuthorizationResult(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().set("Content-Type", "application/json;charset=UTF-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", "unauthorized");
        jsonObject.put("error_description", "invalid_token");
        DataBuffer wrap = response.bufferFactory().wrap(jsonObject.toJSONString().getBytes());
        return response.writeWith(Flux.just(wrap));
    }


    /**
     * 如果请求是不需要token的就返回true，否则返回false
     * @param exchange
     * @return
     */
    private boolean allowNoTokenAccess(ServerWebExchange exchange) {
        String path = exchange.getRequest().getURI().getPath();
        if (noTokenAccessUrls.contains(path)){
            // 不需要token
            return false;
        }
        return true;
    }

    /**
     * 从请求头里面获取token
     * @param exchange
     * @return
     */
    private String getUserToken(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return token ==null ? null : token.replace("bearer ","") ;

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
