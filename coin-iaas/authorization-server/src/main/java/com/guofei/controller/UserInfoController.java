package com.guofei.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/06/29/20:10
 * @Description:
 */
@RestController
public class UserInfoController {

    /**
     * 获取该用户的对象
     * @param principal
     * @return
     */
    @GetMapping("user/info")
    public Principal userInfo(Principal principal){
        return principal;
    }
}
