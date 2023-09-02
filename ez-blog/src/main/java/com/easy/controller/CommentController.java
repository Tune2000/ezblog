package com.easy.controller;

import com.easy.constants.SystemConstants;
import com.easy.domain.ResponseResult;
import com.easy.domain.dto.AddCommentDto;
import com.easy.domain.entity.Comment;
import com.easy.service.CommentService;
import com.easy.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Api(tags = "评论",description = "评论相关的接口")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 查询文章评论列表
    @GetMapping("/commentList")
    @ApiOperation(value = "文章评论列表",notes = "查询文章评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId",value = "文章id"),
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小")
    }
    )
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    // 发送文章评论
    @PostMapping
    @ApiOperation(value = "文章评论列表",notes = "发送文章评论")
    public ResponseResult addComment(@RequestBody AddCommentDto addCommentDto){
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        return commentService.addComment(comment);
    }

    // 查询友链评论列表
    @GetMapping("/linkCommentList")
    @ApiOperation(value = "友链评论列表",notes = "获取一页友链评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小")
    }
    )
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }
}