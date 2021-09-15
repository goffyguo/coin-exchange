package com.guofei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guofei.domain.SysUserRole;
import com.guofei.service.SysUserRoleService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guofei.mapper.SysUserMapper;
import com.guofei.domain.SysUser;
import com.guofei.service.SysUserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * @Author: GuoFei
 * @Date: 2021/07/09/13:51
 * @Description: 
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Override
    public Page<SysUser> findByPage(Page<SysUser> page, String mobile, String fullname) {

        Page<SysUser> pageData = page(page, new LambdaQueryWrapper<SysUser>()
                .like(!StringUtils.isEmpty(mobile), SysUser::getMobile, mobile)
                .like(!StringUtils.isEmpty(fullname), SysUser::getFullname, fullname)
        );

        List<SysUser> records = pageData.getRecords();
        if (!CollectionUtils.isEmpty(records)){
            for (SysUser record : records) {
                List<SysUserRole> userRoles = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>()
                        .eq(SysUserRole::getUserId, record.getId())
                );
                if (!CollectionUtils.isEmpty(userRoles)){
                    record.setRole_strings(
                            userRoles.stream().
                                    map(sysUserRole -> sysUserRole.getRoleId().toString())
                                    .collect(Collectors.joining(",")));

                }
            }
        }

        return  pageData;
    }

    /**
     * 新增员工
     *
     * @param sysUser
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean addUser(SysUser sysUser) {
        // 1 用户的密码
        String password = sysUser.getPassword();
        // 用户的角色Ids
        String role_strings = sysUser.getRole_strings();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        // 加密密码
        String encode = bCryptPasswordEncoder.encode(password);
        // 设置密码
        sysUser.setPassword(encode);
        boolean save = super.save(sysUser);
        if(save){
            // 给用户新增角色数据
            if(!StringUtils.isEmpty(role_strings)){
                String[] roleIds = role_strings.split(",");
                List<SysUserRole> sysUserRoleList = new ArrayList<>(roleIds.length) ;
                for (String roleId : roleIds) {
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setRoleId(Long.valueOf(roleId));
                    sysUserRole.setUserId(sysUser.getId());
                    sysUserRoleList.add(sysUserRole) ;
                }
                sysUserRoleService.saveBatch(sysUserRoleList) ;
            }
        }
        return save;

    }
}
