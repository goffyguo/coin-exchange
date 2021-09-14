package com.guofei.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/08/05/16:11
 * @Description: 自动填充处理器
 */
@Slf4j
@Component
public class AutoFillHandler implements MetaObjectHandler {
    /**
     * 新增时填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        Long userId = getUserId();
        this.strictInsertFill(metaObject,"lastUpdateTime", Date.class,new Date());
        this.strictInsertFill(metaObject,"createBy",Long.class,userId);
        this.strictInsertFill(metaObject,"created",Date.class,new Date());
    }


    /**
     * 修改时填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        Long userId = getUserId();
        this.strictUpdateFill(metaObject,"lastUpdateTime", Date.class,new Date());
        this.strictUpdateFill(metaObject,"modifyBy",Long.class,userId);
    }

    /**
     * 获取用户ID
     * @return
     */
    private Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userID = null;
        if (authentication != null) {
            String s = authentication.getPrincipal().toString();
            userID = Long.valueOf(s);
        }
        return userID;

    }
}
