package com.easy.service;

import com.easy.domain.ResponseResult;
import com.easy.domain.entity.User;

public interface LoginService {
    ResponseResult login(User user);

}