package com.easy.controller;

import com.easy.domain.ResponseResult;
import com.easy.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wxx
 * @create 07--24--14:04
 * @description:
 */
@RestController
@RequestMapping("/article")
@Api(tags = "文章",description = "文章相关的接口")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //查询热门文章列表
    @GetMapping("/hotArticleList")
    @ApiOperation(value = "热门文章列表",notes = "获取热门文章")
    public ResponseResult hotArticleList() {

        ResponseResult result = articleService.hotArticleList();

        return result;
    }

    //分页查询文章的列表
    @GetMapping("/articleList")
    @ApiOperation(value = "文章列表",notes = "获取文章列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小"),
            @ApiImplicitParam(name = "categoryId",value = "分类id")
    }
    )
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    //查询文章详情
    @GetMapping("/{id}")
    //路径参数形式的HTTP请求，注意只有加@PathVariable注解才能接收路径参数形式的HTTP请求
    @ApiOperation(value = "获取文章详情",notes = "获取文章详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "文章id")
    }
    )
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){//注解里指定的id跟上一行保持一致
        //根据id查询文章详情
        return articleService.getArticleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    @ApiOperation(value = "文章浏览量",notes = "更新文章浏览量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "文章id")
    })

    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }
}
