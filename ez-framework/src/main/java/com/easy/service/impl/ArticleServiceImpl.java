package com.easy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.constants.SystemConstants;
import com.easy.domain.ResponseResult;
import com.easy.domain.entity.Article;
import com.easy.domain.vo.HotArticleVo;
import com.easy.mapper.ArticleMapper;
import com.easy.service.ArticleService;
import com.easy.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wxx
 * @create 07--24--12:40
 * @description:
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Override
    public ResponseResult hotArticleList() {
        // 查询热门文章，封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 必须是正式文章，不能是草稿
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 最多查询10条数据
        Page<Article> page = new Page<>(1,10);
        page(page,queryWrapper);

        List<Article> articles = page.getRecords();

        // // bean拷贝（旧）
        // ArrayList<hotArticleVo> articleVos = new ArrayList<>();
        //
        // for (Article article : articles) {
        //     hotArticleVo vo = new hotArticleVo();
        //     BeanUtils.copyProperties(article,vo);
        //     articleVos.add(vo);
        // }

        // 使用封装的bean拷贝工具类
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(hotArticleVos);
    }
}









