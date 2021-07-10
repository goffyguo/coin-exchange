package com.guofei.config.jetcache;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/07/05/18:07
 * @Description:
 */
@Configuration
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.guofei.service.impl")
public class JetCacheConfig {
}
