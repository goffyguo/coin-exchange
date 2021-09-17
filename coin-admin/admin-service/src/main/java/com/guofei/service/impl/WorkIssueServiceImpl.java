package com.guofei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guofei.mapper.WorkIssueMapper;
import com.guofei.domain.WorkIssue;
import com.guofei.service.WorkIssueService;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * @Author: GuoFei
 * @Date: 2021/07/09/13:51
 * @Description: 
 */
@Service
public class WorkIssueServiceImpl extends ServiceImpl<WorkIssueMapper, WorkIssue> implements WorkIssueService{

    /**
     * 条件分页查询工单列表
     *
     * @param page      分页参数
     * @param status    工单的状态
     * @param startTime 查询的工单创建起始时间
     * @param endTime   查询的工单创建截至时间
     * @return
     */
    @Override
    public Page<WorkIssue> findByPage(Page<WorkIssue> page, Integer status, String startTime, String endTime) {
        return page(page, new LambdaQueryWrapper<WorkIssue>()
                .eq(status!=null ,WorkIssue::getStatus,status)
                .between(
                        !StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime),
                        WorkIssue::getCreated,
                        startTime,endTime+" 23:59:59" ));
    }
}
