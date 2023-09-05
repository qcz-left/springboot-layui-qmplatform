package com.qcz.qmplatform.module.business.system.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.module.business.system.domain.UserOrganization;
import com.qcz.qmplatform.module.business.system.mapper.UserOrganizationMapper;
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
public class UserOrganizationService extends ServiceImpl<UserOrganizationMapper, UserOrganization> {

    /**
     * 更新用户部门关联信息
     *
     * @param userIds 需要更新的用户ID集合
     * @param deptId  更新为部门ID
     */
    public void updateDeptByUserIds(List<String> userIds, String deptId) {
        super.update(
                Wrappers.lambdaUpdate(UserOrganization.class)
                        .set(UserOrganization::getOrganizationId, deptId)
                        .in(UserOrganization::getUserId, userIds)
        );
    }

    /**
     * 根据部门ID删除用户部门关联信息
     *
     * @param deptIds 部门ID集合
     */
    public void deleteByDeptIds(List<String> deptIds) {
        super.remove(
                Wrappers.lambdaQuery(UserOrganization.class)
                        .in(UserOrganization::getOrganizationId, deptIds)
        );
    }

    /**
     * 根据用户ID删除用户部门关联信息
     *
     * @param userIds 用户ID集合
     */
    public void deleteByUserIds(List<String> userIds) {
        super.remove(
                Wrappers.lambdaQuery(UserOrganization.class)
                        .in(UserOrganization::getUserId, userIds)
        );
    }
}
