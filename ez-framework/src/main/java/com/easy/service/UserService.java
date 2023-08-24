package com.easy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.domain.ResponseResult;
import com.easy.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author wxx
 * @since 2023-08-12 13:22:09
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

}
