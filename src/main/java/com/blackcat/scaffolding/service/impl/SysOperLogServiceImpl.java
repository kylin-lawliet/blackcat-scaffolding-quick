package com.blackcat.scaffolding.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blackcat.scaffolding.common.result.AjaxResult;
import com.blackcat.scaffolding.common.result.CustomPage;
import com.blackcat.scaffolding.entity.SysOperLog;
import com.blackcat.scaffolding.dao.SysOperLogMapper;
import com.blackcat.scaffolding.service.SysOperLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p> 操作日志记录 服务实现类
 * @author blackcat 2024-08-23
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements SysOperLogService {

    @Autowired
    private SysOperLogMapper sysOperLogMapper;

    @Override
    public AjaxResult getList(Integer pageNow, Integer pageSize) {
        Page<SysOperLog> page = new Page<>(pageNow, pageSize);
        Page<SysOperLog> result = sysOperLogMapper.selectPage(page, null);
        System.out.println(result);
        return AjaxResult.success(result);
        // 使用PageHelper的startPage方法开始分页
//        PageHelper.startPage(pageNow, pageSize);
//        List<SysOperLog> sysOperLogList = sysOperLogMapper.selectList(null);
//        // 使用PageInfo包装查询结果，PageInfo中包含了分页信息，如总记录数
//        PageInfo<SysOperLog> pageInfo = new PageInfo<>(sysOperLogList);
//        return AjaxResult.success(pageInfo);
    }

    @Override
    public AjaxResult getList2(Integer pageNow, Integer pageSize) {
        Page<SysOperLog> page = new Page<>(pageNow, pageSize);
        Page<SysOperLog> result = sysOperLogMapper.selectPage(page, null);
        return AjaxResult.success(CustomPage.MybatisPage(result));
    }
}
