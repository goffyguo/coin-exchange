package com.guofei.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/06/29/19:35
 * @Description: 授权服务器配置
 * 授权服务器配置总结：三大块，关联记忆
 * 既然要完成认证，它首先得知道客户端的信息从哪里读取，因此要进行客户端详情配置
 * 既然要颁发token,那必须定义token的相关Endpoints，以及token如何存取，以及客户端支持哪些类型的token
 * 既然暴露了一些Endpoints，那对这些Endpoints可以定义一些安全上的约束等
 */
@EnableAuthorizationServer
@Configuration
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 认证管理器（密码模式必须）
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    /*@Autowired
    private RedisConnectionFactory redisConnectionFactory;*/


    /**
     * 配置第三方客户端,客户端详情信息在这里进行初始化，你需要把客户端详情信息写死在这里活着通过数据库来存储调取详情信息
     * @param clients
     * @throws Exception
     * 最终会配置在数据库中
     */
    //密码模式
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //第三方客户端名称
                .withClient("coin-api")
                //第三方客户端的密钥
                .secret(passwordEncoder.encode("coin-secret"))
                //第三方客户端的授权范围
                .scopes("all")
                .authorizedGrantTypes("password","refresh_token")
                // token有效期设置为一周
                .accessTokenValiditySeconds(7 * 24 * 3600)
                // refreshToken有效期设置为一月
                .refreshTokenValiditySeconds(30 * 24 * 3600);
    }

    /**
     * 授权码模式
     * @param clients
     * @throws Exception
     */
    /*@Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //第三方客户端名称
                .withClient("coin-api")
                //第三方客户端的密钥
                .secret(passwordEncoder.encode("coin-secret"))
                //第三方客户端的授权范围
                .scopes("all")
                .authorizedGrantTypes("authorization_code","password","refresh_token")
                // 跳转到授权页面
                .autoApprove(false)
                // 加上验证回调地址
                .redirectUris("http://www.baidu.com");
    }*/

    /**
     * 配置验证管理器 userDetailsService
     * 用来配置令牌（token）的访问端点和令牌服务（tokenServices）
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(jwtTokenStore())
                // 允许post提交
                //.allowedTokenEndpointRequestMethods(HttpMethod.POST)
                .tokenEnhancer(jwtAccessTokenConverter())
        ;
        super.configure(endpoints);
    }


    /*@Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security
                // oauth/token_key公开
                .tokenKeyAccess("permitAll()")
                // oauth/check_key公开
                .checkTokenAccess("permitAll()")
                // 表单认证，申请令牌
                .allowFormAuthenticationForClients()
                ;
    }*/

    public TokenStore jwtTokenStore(){
        JwtTokenStore jwtTokenStore = new JwtTokenStore(jwtAccessTokenConverter());
        return jwtTokenStore;
    }

    private JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // 加载私钥
        ClassPathResource classPathResource = new ClassPathResource("coinexchange.jks");
        // 读取KeyStoreKeyFactory
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource, "coinexchange".toCharArray());
        // 给JwtAccessTokenConverter 设置一个密钥对
        jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("coinexchange","coinexchange".toCharArray()));
        return jwtAccessTokenConverter;
    }



   /* public TokenStore tokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
        //return null;
    }*/
}
