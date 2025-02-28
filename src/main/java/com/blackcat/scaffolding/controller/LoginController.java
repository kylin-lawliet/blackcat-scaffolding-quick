package com.blackcat.scaffolding.controller;

import com.blackcat.scaffolding.common.annotation.Log;
import com.blackcat.scaffolding.common.exception.ServiceException;
import com.blackcat.scaffolding.common.result.AjaxResult;
import com.blackcat.scaffolding.common.security.TokenBlacklist;
import com.blackcat.scaffolding.enums.BusinessType;
import com.blackcat.scaffolding.model.LoginBody;
import com.blackcat.scaffolding.service.LoginService;
import com.blackcat.scaffolding.service.SysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


/**
 * 登录
 * @author : zhangdahui  2024/8/23 下午4:45
 */
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private TokenBlacklist tokenBlacklist;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody login) {
        System.out.println("请求到达LoginController的login方法");
        System.out.println("接收到的登录信息：" + login);
        try {
            return loginService.login(login);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("登录失败：" + e.getMessage());
        }
    }

    @GetMapping("/loginOut")
    public AjaxResult loginOut(@RequestHeader("Authorization") String token) {
        // 提取 Token
        String jwtToken = token.replace("Bearer ", "");
        // 将 Token 加入黑名单
        tokenBlacklist.addToBlacklist(jwtToken);
        // 清空 SecurityContextHolder 中的用户信息
        SecurityContextHolder.clearContext();
        return AjaxResult.success();
    }

}
