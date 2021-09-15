package com.guofei.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guofei.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * Created with IntelliJ IDEA.
 * @Author: GuoFei
 * @Date: 2021/07/09/13:51
 * @Description: 
 */
public interface SysUserService extends IService<SysUser>{

    /**
     * 分页查询员工
     * @param page 分页参数
     * @param mobile 员工的手机号
     * @param fullname 员工的全名称
     * @return
     */
    Page<SysUser> findByPage(Page<SysUser> page, String mobile, String fullname);


    /**
     * 新增员工
     * @param sysUser
     * @return
     */
    boolean addUser(SysUser sysUser);
}
