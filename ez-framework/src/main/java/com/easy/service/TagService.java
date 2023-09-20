package com.easy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.domain.ResponseResult;
import com.easy.domain.dto.TagListDto;
import com.easy.domain.entity.Tag;
import com.easy.domain.vo.PageVo;
import com.easy.domain.vo.TagVo;


/**
 * 标签(Tag)表服务接口
 *
 * @author wxx
 * @since 2023-09-05 10:28:11
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(TagListDto tagListDto);

    ResponseResult deleteTag(Long id);

    ResponseResult getLableById(Long id);

    ResponseResult updateById(TagVo tagVo);
}
