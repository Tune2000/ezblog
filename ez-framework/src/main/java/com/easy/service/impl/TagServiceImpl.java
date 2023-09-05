package com.easy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.domain.entity.Tag;
import com.easy.mapper.TagMapper;
import com.easy.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author wxx
 * @since 2023-09-05 10:28:11
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}
