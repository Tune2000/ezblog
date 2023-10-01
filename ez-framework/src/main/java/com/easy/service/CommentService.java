package com.easy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.domain.ResponseResult;
import com.easy.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author wxx
 * @since 2023-08-12 12:37:34
 */
public interface CommentService extends IService<Comment> {
    //查询评论区的评论。增加了commentType参数，用于区分文章的评论区、友链的评论区
    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);
    //在文章的评论区发送评论
    ResponseResult addComment(Comment comment);
}
