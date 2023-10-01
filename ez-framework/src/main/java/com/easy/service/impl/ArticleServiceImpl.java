package com.easy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.constants.SystemConstants;
import com.easy.domain.ResponseResult;
import com.easy.domain.dto.AddArticleDto;
import com.easy.domain.dto.ArticleDto;
import com.easy.domain.entity.Article;
import com.easy.domain.entity.ArticleTag;
import com.easy.domain.entity.Category;
import com.easy.domain.vo.*;
import com.easy.mapper.ArticleMapper;
import com.easy.service.ArticleService;
import com.easy.service.ArticleTagService;
import com.easy.service.CategoryService;
import com.easy.utils.BeanCopyUtils;
import com.easy.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wxx
 * @create 07--24--12:40
 * @description:
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private RedisCache redisCache;

    //查询热门文章列表
    @Override
    public ResponseResult hotArticleList() {
        // 查询热门文章，封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 必须是正式文章，不能是草稿
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 最多查询10条数据
        Page<Article> page = new Page<>(SystemConstants.ARTICLE_STATUS_CURRENT,SystemConstants.ARTICLE_STATUS_SIZE);
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

    //分页查询文章的列表
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //判空。如果前端传了categoryId这个参数，那么查询时要和传入的相同。第二个参数是数据表的文章id，第三个字段是前端传来的文章id
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0 ,Article::getCategoryId,categoryId);
        //只查询状态是正式发布的文章。Article实体类的status字段跟0作比较，一样就表示是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //对isTop字段进行降序排序，实现置顶的文章(isTop值为1)在最前面
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        /**
         * 解决categoryName字段没有返回值的问题。在分页之后，封装成ArticleListVo之前，进行处理
         */
        List<Article> articles = page.getRecords();

        //查询categoryName
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        //articleId去查询articleName进行设置
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }

        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        //把封装的查询结果和文章总数封装在PageVo(我们写的实体类)
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        //转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if(category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应 id的浏览量
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    //（新增）写博文
    public ResponseResult add(AddArticleDto articleDto) {
        //添加 博客
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);

        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    //分页查询文章
    public PageVo selectArticlePage(Article article, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(article.getTitle()),Article::getTitle,article.getTitle());
        queryWrapper.like(StringUtils.hasText(article.getSummary()),Article::getSummary,article.getSummary());

        Page<Article> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);

        //转换成VO
        List<Article> articles = page.getRecords();

        PageVo pageVo = new PageVo();
        pageVo.setTotal(page.getTotal());
        pageVo.setRows(articles);
        return pageVo;
    }

    @Override
    //修改文章
    //1.根据文章id查询文章
    public ArticleByIdVo getInfo(Long id) {
        Article article = getById(id);
        //获取关联标签
        LambdaQueryWrapper<ArticleTag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ArticleTag::getArticleId,article.getId());
        List<ArticleTag> articleTags = articleTagService.list(lambdaQueryWrapper);
        List<Long> tags = articleTags.stream()
                                     .map(articleTag -> articleTag.getTagId())
                                     .collect(Collectors.toList());
        ArticleByIdVo articleVo = BeanCopyUtils.copyBean(article, ArticleByIdVo.class);
        articleVo.setTags(tags);
        return articleVo;
    }

    @Override
    //修改文章
    //2.修改文章
    public void edit(ArticleDto articleDto) {
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        //更新博客信息
        updateById(article);
        //删除原有的标签和博客的关联
        LambdaQueryWrapper<ArticleTag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ArticleTag::getArticleId,article.getId());
        articleTagService.remove(lambdaQueryWrapper);
        //添加新的博客和标签的关联信息
        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(articleDto.getId(), tagId))
                .collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);
    }
}









