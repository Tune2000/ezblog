package com.easy.controller;

import com.easy.domain.ResponseResult;
import com.easy.domain.entity.User;
import com.easy.enums.AppHttpCodeEnum;
import com.easy.exception.SystemException;
import com.easy.service.BlogLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登录",description = "登录相关的接口")
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")

    @ApiOperation(value = "登录")

    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())) {
            // 提示：必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}