package com.guofei.service.impl;

import com.guofei.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guofei.domain.SysMenu;
import com.guofei.mapper.SysMenuMapper;
import com.guofei.service.SysMenuService;
/**
 * Created with IntelliJ IDEA.
 * @Author: GuoFei
 * @Date: 2021/07/09/13:51
 * @Description: 
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService{

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> getMenusByUserId(Long userId) {

        // 1、如果该用户是超级管理员，拥有所有菜单
        if (sysRoleService.isSuperAdmin(userId)){
            return list();
        }
        // 2、如果该用户不是超级管理员 ->> 查询角色 ->> 查询菜单
        return sysMenuMapper.selectMenusByUserId(userId);
    }
}
