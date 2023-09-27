package com.easy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.domain.entity.RoleMenu;


/**
 * 角色和菜单关联表(RoleMenu)表服务接口
 *
 * @author wxx
 * @since 2023-09-26 23:53:48
 */
public interface RoleMenuService extends IService<RoleMenu> {
    //修改角色-保存修改好的角色信息
    void deleteRoleMenuByRoleId(Long id);
}
