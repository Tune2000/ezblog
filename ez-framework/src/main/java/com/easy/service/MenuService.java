package com.easy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author wxx
 * @since 2023-09-05 16:10:54
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}
