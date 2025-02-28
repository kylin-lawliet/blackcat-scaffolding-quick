package com.blackcat.scaffolding.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blackcat.scaffolding.common.result.AjaxResult;
import com.blackcat.scaffolding.common.result.CustomPage;
import com.blackcat.scaffolding.constant.Constant;
import com.blackcat.scaffolding.dao.SysUserMapper;
import com.blackcat.scaffolding.entity.SysUser;
import com.blackcat.scaffolding.model.LoginUser;
import com.blackcat.scaffolding.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.blackcat.scaffolding.constant.Constant.VALID;


/**
 * <p> 用户表 服务实现类
 * @author zhangdahui 2025-02-24
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public LoginUser loadUserByUsername(SysUser sysUser) {
        LoginUser userDetails = new LoginUser();
        BeanUtils.copyProperties(sysUser,userDetails);
        userDetails.setSysUser(sysUser);
        return userDetails;
    }

    @Override
    public AjaxResult selectPage(Integer pageNow, Integer pageSize, String userName) {
        Page<SysUser> page = new Page<>(pageNow, pageSize);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.like("user_name", userName);
        }
        Page<SysUser> result = userMapper.selectPage(page, queryWrapper);
        return AjaxResult.success(CustomPage.mybatisPage(result));
    }

    @Override
    public AjaxResult edit(SysUser obj) {
        SysUser user = JSON.parseObject(JSON.toJSONString(Constant.threadLocal.get()), SysUser.class);
        System.out.println("Constant.threadLocal.get() "+Constant.threadLocal.get());
        obj.setCreateBy(String.valueOf(user.getUserId()));
        obj.setCreateTime(new Date());
        obj.setValidStatus(VALID);
        if (obj.getUserId() == null) {
            userMapper.insert(obj);
        }else {
            userMapper.updateById(obj);
        }
        return AjaxResult.success();
    }
}
