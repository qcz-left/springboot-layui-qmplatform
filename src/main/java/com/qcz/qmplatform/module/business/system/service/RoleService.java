package com.qcz.qmplatform.module.business.system.service;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.utils.IdUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.business.system.domain.Role;
import com.qcz.qmplatform.module.business.system.domain.RolePermission;
import com.qcz.qmplatform.module.business.system.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色服务实现类
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {

    @Resource
    private RolePermissionService rolePermissionService;

    @Resource
    private UserRoleService userRoleService;

    public List<Role> getRoleList(Role role) {
        String roleName = role.getRoleName();
        LambdaQueryWrapper<Role> wrapper = Wrappers.lambdaQuery(Role.class)
                .like(StringUtils.isNotBlank(roleName), Role::getRoleName, roleName);
        return list(wrapper);
    }

    public boolean addRoleOne(Role role) {
        role.setRoleId(IdUtils.getUUID());
        return save(role);
    }

    public boolean updateRoleOne(Role role) {
        return updateById(role);
    }

    public void deleteRole(List<String> roleIds) {
        removeByIds(roleIds);
        userRoleService.deleteByRoleIds(roleIds);
        rolePermissionService.deleteByRoleIds(roleIds);
    }

    public boolean saveRoleOne(Role role) {
        if (StringUtils.isBlank(role.getRoleId())) {
            return addRoleOne(role);
        }
        return updateRoleOne(role);
    }

    public boolean saveRolePermission(String roleId, List<String> permissionIds) {
        rolePermissionService.deleteByRoleId(roleId);

        List<RolePermission> saveRolePermissions = new ArrayList<>();
        for (String permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            saveRolePermissions.add(rolePermission);
        }
        return rolePermissionService.saveBatch(saveRolePermissions, saveRolePermissions.size());
    }

    public List<String> getRolePermission(String roleId) {
        return baseMapper.getPermissionIdsByRoleId(roleId);
    }

    /**
     * 校验角色标识唯一
     *
     * @param roleSign 角色标识
     * @param roleId   角色ID
     */
    public boolean validateRoleSign(String roleSign, String roleId) {
        Assert.notBlank(roleSign);
        LambdaQueryWrapper<Role> wrapper = Wrappers.lambdaQuery(Role.class)
                .ne(StringUtils.isNotBlank(roleId), Role::getRoleId, roleId)
                .eq(Role::getRoleSign, roleSign);
        return baseMapper.selectCount(wrapper) == 0;
    }
}
