package com.easy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author wxx
 * @since 2023-09-05 17:19:10
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}
