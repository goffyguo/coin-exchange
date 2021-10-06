package com.guofei.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guofei.domain.User;
import com.guofei.model.R;
import com.guofei.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/10/06/22:07
 * @Description:
 */
@Api(tags = "会员控制器")
@RestController
public class UserController {

    @Resource
    private UserService userService ;

    @GetMapping
    @ApiOperation(value = "查询会员的列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页"),
            @ApiImplicitParam(name = "size",value = "每页显示的条数"),
            @ApiImplicitParam(name = "mobile",value = "会员的手机号"),
            @ApiImplicitParam(name = "userId",value = "会员的Id,精确查询"),
            @ApiImplicitParam(name = "userName",value = "会员的名称"),
            @ApiImplicitParam(name = "realName",value = "会员的真实名称"),
            @ApiImplicitParam(name = "status",value = "会员的状态")

    })
    @PreAuthorize("hasAuthority('user_query')")
    public R<Page<User>> findByPage(@ApiIgnore Page<User> page ,
                                    String mobile ,
                                    Long userId ,
                                    String userName ,
                                    String realName ,
                                    Integer status
    ){
        page.addOrder(OrderItem.desc("last_update_time")) ;
        Page<User> userPage = userService.findByPage(page,mobile,userId,userName,realName,status) ;
        return R.ok(userPage) ;
    }
}
