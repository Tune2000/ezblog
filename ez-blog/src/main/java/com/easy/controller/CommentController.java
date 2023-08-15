package com.easy.controller;

import com.easy.constants.SystemConstants;
import com.easy.domain.ResponseResult;
import com.easy.domain.entity.Comment;
import com.easy.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 查询文章评论列表
    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    // 发送文章评论
    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    // 查询友链评论列表
    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }
}