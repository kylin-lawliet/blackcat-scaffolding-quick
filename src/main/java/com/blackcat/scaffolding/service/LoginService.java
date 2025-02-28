package com.blackcat.scaffolding.service;

import com.blackcat.scaffolding.common.result.AjaxResult;
import com.blackcat.scaffolding.model.LoginBody;

/**
 * @author : zhangdahui  2025/2/24 上午9:42
 */
public interface LoginService {

    AjaxResult login(LoginBody login);
}
