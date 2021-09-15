package com.guofei.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guofei.domain.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * Created with IntelliJ IDEA.
 * @Author: GuoFei
 * @Date: 2021/07/09/13:51
 * @Description: 
 */
public interface NoticeService extends IService<Notice>{


    /**
     * 条件查询公告
     * @param page 分页参数
     * @param title 公告的标签
     * @param startTime 公告创建的开始时间
     * @param endTime 公告创建的结束时间
     * @param status 公告当前的状态
     * @return
     */
    Page<Notice> findByPage(Page<Notice> page, String title, String startTime, String endTime, Integer status);

}
