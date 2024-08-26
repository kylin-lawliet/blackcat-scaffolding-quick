package com.blackcat.scaffolding.service;

import com.blackcat.scaffolding.common.result.AjaxResult;
import com.blackcat.scaffolding.entity.SysOperLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p> 操作日志记录 服务类
 * @author blackcat 2024-08-23
 */
public interface SysOperLogService extends IService<SysOperLog> {

    AjaxResult getList(Integer pageNow, Integer pageSize);

    AjaxResult getList2(Integer pageNow, Integer pageSize);

}
