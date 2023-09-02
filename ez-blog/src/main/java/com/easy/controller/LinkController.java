package com.easy.controller;

import com.easy.domain.ResponseResult;
import com.easy.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxx
 * @create 08--01--11:11
 * @description:
 */
@RestController
@RequestMapping("/link")
@Api(tags = "链接",description = "链接相关的接口")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/getAllLink")
    @ApiOperation(value = "文章链接",notes = "获取文章链接")
    public ResponseResult getAllLink() {
        return linkService.getAllLink();
    }
}
