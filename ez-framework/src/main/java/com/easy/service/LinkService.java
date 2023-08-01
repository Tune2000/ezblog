package com.easy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.domain.ResponseResult;
import com.easy.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author wxx
 * @since 2023-08-01 11:15:21
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}
