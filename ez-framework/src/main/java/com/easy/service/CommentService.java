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
    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
