package com.qcz.qmplatform.module.business.system.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.module.business.system.domain.RolePermission;
import com.qcz.qmplatform.module.business.system.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
@Service
public class RolePermissionService extends ServiceImpl<RolePermissionMapper, RolePermission> {

    /**
     * 根据角色ID删除角色权限关系
     *
     * @param roleId 角色ID
     */
    public void deleteByRoleId(String roleId) {
        deleteByRoleIds(CollectionUtil.newArrayList(roleId));
    }

    /**
     * 根据角色ID删除角色权限关系
     *
     * @param roleIds 角色ID集合
     */
    public void deleteByRoleIds(List<String> roleIds) {
        super.remove(
                Wrappers.lambdaQuery(RolePermission.class)
                        .in(RolePermission::getRoleId, roleIds)
        );
    }

    /**
     * 根据权限ID删除角色权限关系
     *
     * @param permissionIds 权限ID集合
     */
    public void deleteByPermissionIds(List<String> permissionIds) {
        super.remove(
                Wrappers.lambdaQuery(RolePermission.class)
                        .in(RolePermission::getPermissionId, permissionIds)
        );
    }
}
