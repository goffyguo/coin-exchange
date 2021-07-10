package com.guofei.controller;

import com.guofei.model.LoginResult;
import com.guofei.service.SysLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/07/09/15:23
 * @Description: 登录控制器
 */
@RestController
@Api(tags = "登录控制器")
public class SysLoginController {

    @Autowired
    private SysLoginService sysLoginService;

    @PostMapping("/login")
    @ApiOperation(value = "后台管理人员登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名"),
            @ApiImplicitParam(name = "password",value = "密码")
    })
    public LoginResult login(@RequestParam(required = true)String username,
                             @RequestParam(required = true)String password){
        return sysLoginService.login(username,password);
    }
}
