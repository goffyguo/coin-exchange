package com.guofei.service;

import com.guofei.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @Author: GuoFei
 * @Date: 2021/07/09/13:51
 * @Description: 
 */
public interface SysMenuService extends IService<SysMenu>{

    /**
     * 根据用户ID查询用户菜单
     * @param userId
     * @return
     */
    List<SysMenu> getMenusByUserId(Long userId);
}
