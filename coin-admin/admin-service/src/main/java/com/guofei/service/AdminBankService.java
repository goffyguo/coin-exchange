package com.guofei.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guofei.domain.AdminBank;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * Created with IntelliJ IDEA.
 * @Author: GuoFei
 * @Date: 2021/09/17/19:49
 * @Description: 
 */
public interface AdminBankService extends IService<AdminBank>{


        Page<AdminBank> findByPage(Page<AdminBank> page, String bankCard);

    }
