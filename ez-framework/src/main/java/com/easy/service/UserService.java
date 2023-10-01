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
    //查询个人信息
    ResponseResult userInfo();

    //更新个人信息
    ResponseResult updateUserInfo(User user);

    //用户注册功能
    ResponseResult register(User user);

    //查询用户列表
    ResponseResult selectUserPage(User user, Integer pageNum, Integer pageSize);

    //新增用户-2.新增用户
    boolean checkUserNameUnique(String userName);
    boolean checkPhoneUnique(User user);
    boolean checkEmailUnique(User user);
    ResponseResult addUser(User user);

    //修改用户-②更新用户信息
    void updateUser(User user);
}
