package com.guofei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guofei.mapper.UserMapper;
import com.guofei.domain.User;
import com.guofei.service.UserService;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * @Author: GuoFei
 * @Date: 2021/09/19/22:59
 * @Description: 
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    /**
     * @param page
     * @param mobile
     * @param userId
     * @param userName
     * @param realName
     * @param status
     * @return
     */
    @Override
    public Page<User> findByPage(Page<User> page, String mobile, Long userId,
                                 String userName, String realName, Integer status) {
        return page(page, new LambdaQueryWrapper<User>()
                        .like(!StringUtils.isEmpty(mobile),User::getMobile,mobile)
                        .like(!StringUtils.isEmpty(userName),User::getUsername,userName)
                        .like(!StringUtils.isEmpty(realName),User::getRealName,realName)
                        .eq(userId!=null ,User::getId ,userId)
                        .eq(status!=null ,User::getStatus ,status)
        );
    }
}
