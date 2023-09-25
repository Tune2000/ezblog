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

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto article);

    PageVo selectArticlePage(Article article, Integer pageNum, Integer pageSize);

    //修改文章 1.根据文章id查询文章
    ArticleByIdVo getInfo(Long id);

    //修改文章 2.修改文章
    void edit(ArticleDto articleDto);
}
