package com.guofei.feign;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/07/09/15:45
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "登录的结果")
public class JwtToken {
    /**
     * access_token
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * token 类型
     */
    @JsonProperty("token_type")
    private String tokenType;

    /**
     * refresh_token
     */
    @JsonProperty("refresh_token")
    private String refreshToken;

    /**
     * 过期时间
     */
    @JsonProperty("expires_in")
    private Long expiresIn;


    /**
     * token的范围
     */
    private String scope;

    /**
     * 颁发的凭证
     */
    private String jti;

}
