package com.guofei.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/06/29/19:43
 * @Description:
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 注入一个验证管理器，如果选择了资源所有者密码（password）授权类型的时候，需要设置这个属性
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    /**
     * 资源的放行
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭csrf
        //http.csrf().disable();
        //http.authorizeRequests().anyRequest().authenticated();
        //拦截所有请求 通过httpBasic进行认证
        http.authorizeRequests().antMatchers("/**").fullyAuthenticated().and().httpBasic();

    }

   /* @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        User user = new User("admin", "123456", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        inMemoryUserDetailsManager.createUser(user);
        return inMemoryUserDetailsManager;
    }*/

    /**
     * 注入密码验证管理器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
