package com.guofei.controller;

import com.guofei.domain.SysUser;
import com.guofei.model.R;
import com.guofei.service.SysUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/07/09/14:29
 * @Description:
 */
@RestController
@Api(tags = "admin-service-test 接口")
public class TestController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/user/info/{id}")
    @ApiOperation(value = "使用用户的id查询用户",authorizations = {@Authorization("Authorization")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "用户id")
    })
    public R<SysUser> sysUserR(@PathVariable("id") Long id){
        SysUser byId = sysUserService.getById(id);
        return R.ok(byId);
    }
}
