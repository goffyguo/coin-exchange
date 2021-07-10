package com.guofei.config.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/07/05/18:12
 * @Description:
 */
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                // 基于token，所以不需要session
                .sessionManagement().disable()
                .authorizeRequests().antMatchers(
                        "/login",
                    "/v2/api-docs",
                    //用来获取支持的动作
                        "/swagger-resources/configuration/ui",
                    //用来获取api-docs的URI
                    "/swagger-resources",
                    //安全选项
                    "/swagger-resources/configuration/security",
                    "/webjars/**",
                    "/swagger-ui.html"
                ).permitAll()
                .antMatchers("/**")
                .authenticated()
                .and()
                .headers()
                .cacheControl();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore());
    }

    private TokenStore tokenStore() {
        JwtTokenStore jwtTokenStore = new JwtTokenStore(jwtAccessTokenConverter());
        return jwtTokenStore;
    }

    private JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        ClassPathResource classPathResource = new ClassPathResource("coinexchange.txt");
        String publicKey = null;
        try {
            byte[] bytes = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
            publicKey = new String(bytes, "utf-8");
        } catch (IOException e) {
            log.info("读取公钥失败");
        }
        tokenConverter.setVerifierKey(publicKey);
        tokenConverter.setVerifier(new RsaVerifier(publicKey));
        return tokenConverter;

    }
}
