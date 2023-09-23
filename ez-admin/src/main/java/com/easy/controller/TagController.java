package com.easy.controller;

import com.easy.domain.ResponseResult;
import com.easy.domain.dto.TagListDto;
import com.easy.domain.vo.PageVo;
import com.easy.domain.vo.TagListVo;
import com.easy.domain.vo.TagVo;
import com.easy.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    @PostMapping
    public ResponseResult addTag(@RequestBody TagListDto tagListDto){
        return tagService.addTag(tagListDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable Long id){
        return tagService.deleteTag(id);
    }

    @GetMapping("{id}")
    public ResponseResult getLableById(@PathVariable Long id) {
        return  tagService.getLableById(id);
    }

    @PutMapping
    public ResponseResult updateById(@RequestBody TagVo tagVo) {
        return tagService.updateById(tagVo);
    }


    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        List<TagListVo> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }
}
