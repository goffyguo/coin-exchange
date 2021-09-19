package com.guofei.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guofei.mapper.UserMapper;
import com.guofei.domain.User;
import com.guofei.service.UserService;
/**
 * Created with IntelliJ IDEA.
 * @Author: GuoFei
 * @Date: 2021/09/19/22:59
 * @Description: 
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

}
