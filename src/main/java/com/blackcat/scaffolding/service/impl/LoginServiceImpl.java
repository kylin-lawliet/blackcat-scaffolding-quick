package com.blackcat.scaffolding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blackcat.scaffolding.common.result.AjaxResult;
import com.blackcat.scaffolding.dao.SysUserMapper;
import com.blackcat.scaffolding.entity.SysUser;
import com.blackcat.scaffolding.model.LoginBody;
import com.blackcat.scaffolding.model.LoginUser;
import com.blackcat.scaffolding.service.LoginService;
import com.blackcat.scaffolding.service.SysUserService;
import com.blackcat.scaffolding.common.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * @author : zhangdahui  2025/2/24 上午9:42
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired(required=true)
    private AuthenticationManager authenticationManager;
    @Autowired
    private SysUserService userService;

    @Override
    public AjaxResult login(LoginBody login) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_name",login.getLoginName());
        queryWrapper.eq("password",login.getPassword());
        SysUser sysUser = userMapper.selectOne(queryWrapper);
        System.out.println("登录用户："+login.getLoginName()+"  登录密码："+login.getPassword());
        if (sysUser == null) {
            return AjaxResult.error("用户不存在或密码错误");
        }
        //存储用户信息
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getLoginName(), login.getPassword())
        );
        LoginUser userDetails = userService.loadUserByUsername(sysUser);
        final String jwt = jwtUtil.generateToken(userDetails);
        userDetails.setToken(jwt);
        return AjaxResult.success(userDetails);
    }
}
