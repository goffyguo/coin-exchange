package com.guofei.model;

import com.guofei.domain.SysMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/07/09/15:21
 * @Description: 登录的返回值
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResult {
    /**
     * 登录产生的token
     */
    private String token;

    /**
     * 前端的菜单数据
     */
    private List<SysMenu> menus;

    /**
     * 权限数据
     */
    private List<SimpleGrantedAuthority> authorities;
}
