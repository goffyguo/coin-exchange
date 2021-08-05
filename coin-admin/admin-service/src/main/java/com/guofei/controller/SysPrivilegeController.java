package com.guofei.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guofei.domain.SysPrivilege;
import com.guofei.model.R;
import com.guofei.service.SysPrivilegeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/08/03/20:37
 * @Description:
 * 权限管理的控制器
 *  * 1 查询
 *  * 2 修改
 *  * 3 新增
 *  * 4 删除
 *  * 权限管理的权限：
 *  * 查询：sys_privilege_query
 *  * 修改：sys_privilege_update
 *  * 新增：sys_privilege_create
 *  * 删除：sys_privilege_delete
 */
@RestController
@RequestMapping("/privileges")
@Api(tags = "权限的管理")
public class SysPrivilegeController {
    @Resource
    private SysPrivilegeService sysPrivilegeService;

    @GetMapping
    @ApiOperation(value = "分页查询权限数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页"),
            @ApiImplicitParam(name = "size",value = "每页显示的大小")
    })
    @PreAuthorize("hasAnyAuthority('sys_privilege_query')")
    public R<Page<SysPrivilege>> findByPage(@ApiIgnore Page<SysPrivilege> page){
        // 查询时，我们将最近新增的、修改的数据优先展示
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<SysPrivilege> sysPrivilegePage = sysPrivilegeService.page(page);
        return R.ok(sysPrivilegePage);
    }

}
