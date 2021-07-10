package com.guofei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/07/05/19:58
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CoinCommonApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoinCommonApplication.class,args);
    }
}
