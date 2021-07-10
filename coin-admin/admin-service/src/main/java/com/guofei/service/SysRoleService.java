package com.guofei.service;

import com.guofei.domain.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * Created with IntelliJ IDEA.
 * @Author: GuoFei
 * @Date: 2021/07/09/13:51
 * @Description: 
 */
public interface SysRoleService extends IService<SysRole>{

        /**
         * 判断一个用户是否是超级管理员
         * @param userId
         * @return
         */
     boolean isSuperAdmin(Long userId);
}
