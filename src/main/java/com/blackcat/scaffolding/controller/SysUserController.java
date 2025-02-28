package com.blackcat.scaffolding.controller;


import com.blackcat.scaffolding.common.annotation.Log;
import com.blackcat.scaffolding.common.result.AjaxResult;
import com.blackcat.scaffolding.entity.SysUser;
import com.blackcat.scaffolding.enums.BusinessType;
import com.blackcat.scaffolding.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p> 用户表 前端控制器
 * @author zhangdahui 2025-02-24
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService userService;

    @GetMapping("/selectPage")
    public AjaxResult selectPage(Integer page, Integer size, String userName) {
        return userService.selectPage(page,size,userName);
    }

    @Log(title = "编辑", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody SysUser obj) {
        return userService.edit(obj);
    }

    @GetMapping("/getOne")
    public AjaxResult getOne(String id) {
        return AjaxResult.success(userService.getById(id));
    }

    @Log(title = "删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete")
    public AjaxResult delete(String id) {
        userService.removeById(id);
        return AjaxResult.success();
    }

    @GetMapping("/getSelectList")
    public AjaxResult getSelectList(String id) {
        return AjaxResult.success(userService.getById(id));
    }


}
