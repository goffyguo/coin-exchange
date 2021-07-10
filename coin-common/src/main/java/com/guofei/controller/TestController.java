package com.guofei.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guofei.model.R;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/07/05/20:08
 * @Description:
 */
@RestController
@Api(tags = "CoinCommon里面测试的接口")
public class TestController {
    @Autowired
    private ObjectMapper objectMapper ;

    @GetMapping("/common/test")
    @ApiOperation(value = "测试方法", authorizations = {@Authorization("Authorization")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "参数1", dataType = "String", paramType = "query", example = "paramValue"),
            @ApiImplicitParam(name = "param1", value = "参数2", dataType = "String", paramType = "query", example = "paramValue")
    })
    public R<String> testMethod(String param, String param1) {

        return R.ok("ok");
    }

    @GetMapping("/common/date")
    @ApiOperation(value = "日志格式化测试", authorizations = {@Authorization("Authorization")})
    public R<Date> testMethod() {
        return R.ok(new Date());
    }


}
