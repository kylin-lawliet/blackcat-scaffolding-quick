package com.blackcat.scaffolding.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blackcat.scaffolding.dao.SysUserMapper;
import com.blackcat.scaffolding.entity.SysUser;
import com.blackcat.scaffolding.model.LoginUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : zhangdahui  2025/2/28 上午10:09
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 实现根据用户名加载用户信息的逻辑
        // 从数据库中查询用户信息
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_name", username);
        SysUser sysUser = userMapper.selectOne(queryWrapper);
        // 如果用户不存在，抛出 UsernameNotFoundException 异常
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户 " + username + " 不存在");
        }
        // 获取用户的角色信息，假设 SysUser 中有一个 getRoles 方法返回用户的角色列表
//        List<String> roles = sysUser.getRoles();
        // 将角色信息转换为 GrantedAuthority 列表
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        for (String role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role));
//        }
        return new User(sysUser.getLoginName(), sysUser.getPassword(), authorities);
    }
}
