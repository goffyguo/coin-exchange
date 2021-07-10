package com.guofei.service.impl;

import com.guofei.constant.LoginConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/06/30/20:44
 * @Description:
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 登陆的实现
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String loginType = requestAttributes.getRequest().getParameter("login_type");
        if (StringUtils.isEmpty(loginType)) {
            throw new AuthenticationServiceException("请添加login_type");
        }
        String grantType = requestAttributes.getRequest().getParameter("grant_type");
        UserDetails userDetails = null;
        try {
            if (LoginConstant.REFRESH_TOKEN.equals(grantType.toUpperCase())){
                username = adjustUsername(username,loginType);
            }
            switch (loginType) {
                case LoginConstant.ADMIN_TYPE:
                    userDetails = loadAdminUserByUserName(username);
                    break;
                case LoginConstant.MEMBER_TYPE:
                    userDetails = loadMemberUserByUserName(username);
                    break;
                default:
                    throw new AuthenticationServiceException("暂不支持的登陆方式" + loginType);
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new UsernameNotFoundException("会员：" + username + "不存在");
        }

        return userDetails;
    }

    /**
     * 纠正在refresh 场景下的登录问题
     * @param username
     * @param loginType
     * @return
     */
    private String adjustUsername(String username, String loginType) {
        if (LoginConstant.ADMIN_TYPE.equals(loginType)) {
            return jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_USER_WITH_ID, String.class, username);
        }
        if (LoginConstant.MEMBER_TYPE.equals(loginType)) {
            return jdbcTemplate.queryForObject(LoginConstant.QUERY_MEMBER_USER_WITH_ID, String.class, username);
        }
        return username;
    }

    private UserDetails loadMemberUserByUserName(String username) {

        return jdbcTemplate.queryForObject(LoginConstant.QUERY_MEMBER_SQL, (RowMapper<UserDetails>) (rs, rowNum) -> {
            if (rs.wasNull()) {
                throw new UsernameNotFoundException("会员：" + username + "不存在");
            }
            long id = rs.getLong("id"); // 获取用户的id
            String password = rs.getString("password");
            int status = rs.getInt("status");
            return new User(
                    String.valueOf(id),
                    password,
                    status == 1,
                    true,
                    true,
                    true,
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
            );
        }, username, username);
    }

    /**
     * 对接管理员的登陆
     *
     * @param username
     * @return
     */
    private UserDetails loadAdminUserByUserName(String username) {
        return jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_SQL, (rs, i) -> {
            if (rs.wasNull()) {
                throw new UsernameNotFoundException("用户：" + username + "不存在");
            }
            long id = rs.getLong("id");
            String password = rs.getString("password");
            int status = rs.getInt("status");
            User user = new User(String.valueOf(id), // 使用用户的id 代替用户的名称，这样会使得后面的很多情况得以处理
                    password,
                    status == 1,
                    true,
                    true,
                    true,
                    getUserPermissions(id)
            );
            return user;
        }, username);
    }

    private Set<SimpleGrantedAuthority> getUserPermissions(long id) {

        // 查询用户是否为管理员
        String code = jdbcTemplate.queryForObject(LoginConstant.QUERY_ROLE_CODE_SQL, String.class, id);
        List<String> permissions = null;
        // 管理员
        if (LoginConstant.ADMIN_ROLE_CODE.equals(code)) {
            permissions = jdbcTemplate.queryForList(LoginConstant.QUERY_ALL_PERMISSIONS, String.class);
        } else {
            permissions = jdbcTemplate.queryForList(LoginConstant.QUERY_PERMISSION_SQL, String.class, id);
        }
        if (permissions == null || permissions.isEmpty()) {
            return Collections.EMPTY_SET;
        }
        return permissions
                .stream()
                .distinct() // 去重
                // perm - >security可以识别的权限
                .map(perm -> new SimpleGrantedAuthority(perm))
                .collect(Collectors.toSet());

    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
    }
}
