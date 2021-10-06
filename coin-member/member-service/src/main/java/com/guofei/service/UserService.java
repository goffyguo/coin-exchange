package com.guofei.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guofei.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * Created with IntelliJ IDEA.
 * @Author: GuoFei
 * @Date: 2021/09/19/22:59
 * @Description: 
 */
public interface UserService extends IService<User>{


    /**
     *
     * @param page
     * @param mobile
     * @param userId
     * @param userName
     * @param realName
     * @param status
     * @return
     */
    Page<User> findByPage(Page<User> page, String mobile,
                          Long userId, String userName,
                          String realName, Integer status);

    }
