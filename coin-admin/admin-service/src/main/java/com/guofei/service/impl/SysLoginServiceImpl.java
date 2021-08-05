package com.guofei.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.guofei.domain.SysMenu;
import com.guofei.feign.JwtToken;
import com.guofei.feign.OAuth2FeignClient;
import com.guofei.model.LoginResult;
import com.guofei.service.SysLoginService;
import com.guofei.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/07/09/15:39
 * @Description:
 */
@Service
@Slf4j
public class SysLoginServiceImpl implements SysLoginService {

    @Autowired
    private OAuth2FeignClient oAuth2FeignClient;

    @Autowired
    private SysMenuService sysMenuService;

    @Value("${basic.token:Basic Y29pbi1hcGk6Y29pbi1zZWNyZXQ=}")
    private String basicToken;

    @Autowired
    private StringRedisTemplate redisTemplate ;


    /**
     * 登录的实现
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录的结果
     */
    @Override
    public LoginResult login(String username, String password) {
        log.info("用户{}开始登录", username);
        // 1、获取 token, 需要远程调用 authorization-server
        ResponseEntity<JwtToken> tokenResponseEntity = oAuth2FeignClient.getToken("password", username, password, "admin_type", basicToken);
        if (tokenResponseEntity.getStatusCode() != HttpStatus.OK) {
            throw new ApiException(ApiErrorCode.FAILED);
        }
        JwtToken jwtToken = tokenResponseEntity.getBody();
        log.info("远程调用授权服务器成功，获取的token为{}", JSON.toJSONString(jwtToken, true));
        String token = jwtToken.getAccessToken();


        // 2、查询菜单数据
        Jwt jwt = JwtHelper.decode(token);
        String jwtTokenStr = jwt.getClaims();
        JSONObject jwtJson = JSON.parseObject(jwtTokenStr);
        Long userId = Long.valueOf(jwtJson.getString("user_name"));
        List<SysMenu> menus = sysMenuService.getMenusByUserId(userId);

        // 3、权限数据查询（不需要查询，因为JWT中自带）
        JSONArray authorities = jwtJson.getJSONArray("authorities");
        List<SimpleGrantedAuthority> collect = authorities.stream()
                                                .map(au -> new SimpleGrantedAuthority(au.toString()))
                                                .collect(Collectors.toList());
        log.info("--redisTemplate--",redisTemplate);
        // 1、将token存储在redis里面，配置网管做jwt的校验
        redisTemplate.opsForValue().set(token,"",jwtToken.getExpiresIn(), TimeUnit.SECONDS);
        // 2、返回给前端的Token 少一个bearer:
        return new LoginResult(jwtToken.getTokenType()+" "+token,menus,collect);
    }
}
