package com.easy.service;

import com.easy.domain.ResponseResult;
import com.easy.domain.entity.User;

/**
 * @author wxx
 * @create 08--07--10:44
 * @description:
 */
public interface BlogLoginService {
    //登录
    ResponseResult login(User user);
    //退出
    ResponseResult logout();
}
