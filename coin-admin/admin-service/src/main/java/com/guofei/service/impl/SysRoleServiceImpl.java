package com.guofei.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guofei.mapper.SysRoleMapper;
import com.guofei.domain.SysRole;
import com.guofei.service.SysRoleService;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * @Author: GuoFei
 * @Date: 2021/07/09/13:51
 * @Description: 
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService{

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public boolean isSuperAdmin(Long userId) {
        // 当用户的code为： ROLE_ADMIN 时，该用户是超级管理员
        String code = sysRoleMapper.getUserRoleCode(userId);
        if (!StringUtils.isEmpty(code) && code.equals("ROLE_ADMIN")){
            return true;
        }
        return false;
    }
}
