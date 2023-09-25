package com.easy.controller;

import com.easy.domain.ResponseResult;
import com.easy.domain.dto.AddArticleDto;
import com.easy.domain.dto.ArticleDto;
import com.easy.domain.entity.Article;
import com.easy.domain.vo.ArticleByIdVo;
import com.easy.domain.vo.PageVo;
import com.easy.service.ArticleService;
import com.easy.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }

    //分页查询文章
    @GetMapping("list")
    public ResponseResult list(Article article, Integer pageNum, Integer pageSize){
        PageVo pageVo = articleService.selectArticlePage(article,pageNum,pageSize);
        return ResponseResult.okResult(pageVo);
    }

    //根据文章id修改文章
    @GetMapping("/{id}")
    //1.根据文章id查询文章
    public ResponseResult getInfo(@PathVariable(value = "id") Long id) {
        ArticleByIdVo article = articleService.getInfo(id);
        return ResponseResult.okResult(article);
    }

    @PutMapping
    //2.修改文章
    public ResponseResult edit(@RequestBody ArticleDto articleDto) {
        articleService.edit(articleDto);
        return ResponseResult.okResult();
    }

    //删除文章
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Long id){
        articleService.removeById(id);
        return ResponseResult.okResult();
    }
}