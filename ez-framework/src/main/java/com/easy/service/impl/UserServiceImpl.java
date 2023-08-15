package com.easy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.domain.entity.User;
import com.easy.mapper.UserMapper;
import com.easy.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author wxx
 * @since 2023-08-12 13:22:09
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
