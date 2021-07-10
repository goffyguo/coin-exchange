package com.guofei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guofei.domain.SysRole;

/**
 * Created with IntelliJ IDEA.
 * @Author: GuoFei
 * @Date: 2021/07/09/13:51
 * @Description: 
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 查询用户角色code
     * @param userId
     * @return
     */
    String getUserRoleCode(Long userId);
}