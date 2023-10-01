package com.easy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.domain.ResponseResult;
import com.easy.domain.dto.AddArticleDto;
import com.easy.domain.dto.ArticleDto;
import com.easy.domain.entity.Article;
import com.easy.domain.vo.ArticleByIdVo;
import com.easy.domain.vo.PageVo;

/**
 * @author wxx
 * @create 07--24--12:39
 * @description:
 */
public interface ArticleService extends IService<Article> {
    //查询热门文章列表
    ResponseResult hotArticleList();
    //分类查询文章列表
    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);
    //根据id查询文章详情
    ResponseResult getArticleDetail(Long id);
    //根据文章id从mysql查询文章
    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto article);

    PageVo selectArticlePage(Article article, Integer pageNum, Integer pageSize);

    //修改文章 1.根据文章id查询文章
    ArticleByIdVo getInfo(Long id);

    //修改文章 2.修改文章
    void edit(ArticleDto articleDto);
}
