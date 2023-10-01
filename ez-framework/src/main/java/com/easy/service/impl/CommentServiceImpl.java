package com.easy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.constants.SystemConstants;
import com.easy.domain.ResponseResult;
import com.easy.domain.entity.Comment;
import com.easy.domain.vo.CommentVo;
import com.easy.domain.vo.PageVo;
import com.easy.enums.AppHttpCodeEnum;
import com.easy.exception.SystemException;
import com.easy.mapper.CommentMapper;
import com.easy.service.CommentService;
import com.easy.service.UserService;
import com.easy.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author wxx
 * @since 2023-08-12 12:37:45
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    //根据userid查询用户信息，也就是查username
    @Autowired UserService userService;

    //查询评论区的评论
    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();

        //对articleId进行判断，作用是得到指定的文章。如果是文章评论，才会判断articleId，避免友链评论判断articleId时出现空指针
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);

        //根评论 rootId为-1
        queryWrapper.eq(Comment::getRootId,SystemConstants.COMMENT_ROOT);

        //文章的评论，避免查到友链的评论
        queryWrapper.eq(Comment::getType,commentType);

        //分页查询。查的是整个评论区的每一条评论
        Page<Comment> page = new Page(pageNum,pageSize);
        page(page,queryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());

        //查询所有根评论对应的子评论集合，并且赋值给对应的属性
        for (CommentVo commentVo : commentVoList) {
            //查询对应的子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            //把查到的children子评论集的集合，赋值给commentVo类的children字段
            commentVo.setChildren(children);
        }

        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
    }

    //在文章的评论区发送评论
    @Override
    public ResponseResult addComment(Comment comment) {
        //评论内容不能为空
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 根据根评论的id查询所对应的子评论的集合
     * @param id 根评论的id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);

        List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;
    }

    private List<CommentVo> toCommentVoList(List<Comment> list){
        //获取评论区的所有评论
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        //遍历。由于封装响应好的数据里面没有username字段，所以我们还不能返回给前端。这个遍历就是用来得到username字段
        for (CommentVo commentVo : commentVos) {
            //需要根据commentVo类里面的createBy字段，然后用createBy字段去查询user表的nickname字段(子评论的用户昵称)
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            //然后把nickname字段(发这条子评论的用户昵称)的数据赋值给commentVo类的username字段
            commentVo.setUsername(nickName);
            //查询根评论的用户昵称。判断getToCommentUserId为1，就表示这条评论是根评论
            if(commentVo.getToCommentUserId()!=-1){
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }
        return commentVos;
    }
}
