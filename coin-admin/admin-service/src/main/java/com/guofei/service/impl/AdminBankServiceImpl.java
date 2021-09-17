package com.guofei.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guofei.domain.AdminBank;
import com.guofei.mapper.AdminBankMapper;
import com.guofei.service.AdminBankService;
/**
 * Created with IntelliJ IDEA.
 * @Author: GuoFei
 * @Date: 2021/09/17/19:49
 * @Description: 
 */
@Service
public class AdminBankServiceImpl extends ServiceImpl<AdminBankMapper, AdminBank> implements AdminBankService{

    @Override
    public Page<AdminBank> findByPage(Page<AdminBank> page, String bankCard) {
        return null;
    }
}
