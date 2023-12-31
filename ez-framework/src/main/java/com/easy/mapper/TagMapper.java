package com.easy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.domain.entity.Tag;
import org.apache.ibatis.annotations.Param;


/**
 * 标签(Tag)表数据库访问层
 *
 * @author wxx
 * @since 2023-09-05 10:28:11
 */
public interface TagMapper extends BaseMapper<Tag> {

    int myTagUpdateById(@Param("id") Long id);
}
