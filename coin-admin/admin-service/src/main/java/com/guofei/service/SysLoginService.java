package com.guofei.service;

import com.guofei.model.LoginResult;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/07/09/15:24
 * @Description:
 */
public interface SysLoginService {
    /**
     * 后台管理人员登录
     * @param username
     * @param password
     * @return
     */
    LoginResult login(String username, String password);
}
