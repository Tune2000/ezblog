package com.easy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.domain.ResponseResult;
import com.easy.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author wxx
 * @since 2023-07-30 11:06:22
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}
