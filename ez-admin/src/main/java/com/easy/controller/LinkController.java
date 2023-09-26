package com.easy.controller;

import com.easy.domain.ResponseResult;
import com.easy.domain.entity.Link;
import com.easy.domain.vo.PageVo;
import com.easy.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wxx
 * @create 09--26--9:46
 * @description:
 */
@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;

    //分页查询友链列表
    @GetMapping("/list")
    private ResponseResult list(Link link,Integer pageNum,Integer pageSize) {
        PageVo pageVo = linkService.selectLinkPage(link,pageNum,pageSize);
        return ResponseResult.okResult(pageVo);
    }

    //新增友链
    @PostMapping
    public ResponseResult add(@RequestBody Link link) {
        linkService.save(link);
        return ResponseResult.okResult();
    }

    //修改友链
    @GetMapping(value = "/{id}")
    //1.根据id查询友链
    public ResponseResult getInfo(@PathVariable(value = "id") Integer id) {
        Link link = linkService.getById(id);
        return ResponseResult.okResult(link);
    }

    //2.修改友链
    @PutMapping
    public ResponseResult edit(@RequestBody Link link){
        linkService.updateById(link);
        return ResponseResult.okResult();
    }

    //删除友链
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Long id){
        linkService.removeById(id);
        return ResponseResult.okResult();
    }
}
