package com.blackcat.scaffolding.controller;

import com.blackcat.scaffolding.common.annotation.Log;
import com.blackcat.scaffolding.common.exception.ServiceException;
import com.blackcat.scaffolding.common.result.AjaxResult;
import com.blackcat.scaffolding.enums.BusinessType;
import com.blackcat.scaffolding.service.SysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 使用示例
 * @author : zhangdahui  2024/8/23 下午4:45
 */
@RestController
public class DomeController {

    @Autowired
    private SysOperLogService sysOperLogService;

    @GetMapping("/testSuccess")
    public AjaxResult testSuccess() {
        return AjaxResult.success();
    }

    @Log(title = "操作日志测试", businessType = BusinessType.OTHER)
    @GetMapping("/testLog")
    public AjaxResult testLog() {
        return AjaxResult.success();
    }

    @GetMapping("/testPagehelper")
    public AjaxResult testPagehelper() {

        return AjaxResult.success();
    }

    @GetMapping("/testError")
    public AjaxResult testError() {
        return AjaxResult.error();
    }

    @GetMapping("/testError2")
    public AjaxResult testError2() {
        return AjaxResult.error("自定义异常信息");
    }

    // 全局异常捕获测试
    @GetMapping("/testError3")
    public AjaxResult testError3() {
        int s = 1/0;
        return AjaxResult.error();
    }

    @GetMapping("/testError4")
    public AjaxResult testError4(String param) {
        if (param.equals("1")) {
            throw  new ServiceException("自定义业务异常抛出");
        }
        return AjaxResult.success();
    }


    // pagination分页插件自带分页类
    @Log(title = "分页插件自带分页类", businessType = BusinessType.OTHER)
    @GetMapping("/getList")
    public AjaxResult getList(Integer pageNow, Integer pageSize) {
        return sysOperLogService.getList(pageNow,pageSize);
    }

    // 自定义分页对象返回
    @Log(title = "自定义分页对象返回", businessType = BusinessType.OTHER)
    @GetMapping("/getList2")
    public AjaxResult getList2(Integer pageNow, Integer pageSize) {
        return sysOperLogService.getList2(pageNow,pageSize);
    }


}
