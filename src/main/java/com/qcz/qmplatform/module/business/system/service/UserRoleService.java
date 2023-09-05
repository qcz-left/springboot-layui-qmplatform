package com.qcz.qmplatform.module.business.system.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.module.business.system.domain.UserRole;
import com.qcz.qmplatform.module.business.system.mapper.UserRoleMapper;
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
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRole> {

    /**
     * 根据用户ID删除用户角色关系
     *
     * @param userIds 用户ID
     */
    public void deleteByUserIds(List<String> userIds) {
        super.remove(
                Wrappers.lambdaQuery(UserRole.class)
                        .in(UserRole::getUserId, userIds)
        );
    }

    /**
     * 根据角色ID删除用户角色关系
     *
     * @param roleIds 角色ID
     */
    public void deleteByRoleIds(List<String> roleIds) {
        super.remove(
                Wrappers.lambdaQuery(UserRole.class)
                        .in(UserRole::getRoleId, roleIds)
        );
    }
}
