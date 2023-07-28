package com.easy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.domain.ResponseResult;
import com.easy.domain.entity.Article;

/**
 * @author wxx
 * @create 07--24--12:39
 * @description:
 */
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();
}
