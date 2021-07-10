package com.guofei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guofei.domain.SysMenu;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @Author: GuoFei
 * @Date: 2021/07/09/13:51
 * @Description: 
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 根据用户id查询菜单
     * @param userId
     * @return
     */
    List<SysMenu> selectMenusByUserId(Long userId);
}