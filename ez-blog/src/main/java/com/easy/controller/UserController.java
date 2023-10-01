package com.easy.controller;

import com.easy.annotation.SystemLog;
import com.easy.domain.ResponseResult;
import com.easy.domain.entity.User;
import com.easy.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(tags = "用户",description = "用户相关的接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @ApiOperation(value = "个人信息",notes = "获取用户信息")
    @SystemLog(BusinessName = "查询个人信息")//接口描述，用于'日志记录'功能
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @ApiOperation(value = "个人信息",notes = "更新用户信息")
    @SystemLog(BusinessName="更新用户信息")//接口描述，用于'日志记录'功能
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    @ApiOperation(value = "个人信息",notes = "用户注册")
    @SystemLog(BusinessName = "用户注册")//接口描述，用于'日志记录'功能
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }
}