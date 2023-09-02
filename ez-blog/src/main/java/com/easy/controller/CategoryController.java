package com.easy.controller;

import com.easy.domain.ResponseResult;
import com.easy.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxx
 * @create 07--30--11:19
 * @description:
 */
@RestController
@Api(tags = "分类",description = "分类相关的接口")
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    @ApiOperation(value = "文章分类",notes = "获取文章分类信息")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }
}
