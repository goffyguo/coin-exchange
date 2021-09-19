package com.guofei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guofei.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created with IntelliJ IDEA.
 * @Author: GuoFei
 * @Date: 2021/09/19/22:59
 * @Description: 
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}