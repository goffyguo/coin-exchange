package com.guofei.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/07/09/15:44
 * @Description:
 */
@FeignClient(value = "authorization-server")
public interface OAuth2FeignClient {


    /**
     * 远程调用 oauth/token
     * @param grantType 授权类型
     * @param username 用户名
     * @param password 密码
     * @param loginType 登录类型
     * @param basicToken Basic Y29pbi1hcGk6Y29pbi1zZWNyZXQ= 由第三方客户端信息加密出现的值
     * @return
     */
    @PostMapping("/oauth/token")
    ResponseEntity<JwtToken> getToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("login_type") String loginType,
            @RequestHeader("Authorization") String basicToken
    );
}
