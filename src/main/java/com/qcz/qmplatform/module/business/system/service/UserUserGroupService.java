package com.qcz.qmplatform.module.business.system.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.module.business.system.domain.UserUserGroup;
import com.qcz.qmplatform.module.business.system.domain.dto.BatchUserUserGroupDTO;
import com.qcz.qmplatform.module.business.system.mapper.UserUserGroupMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户-用户组关联 服务实现类
 * </p>
 *
 * @author quchangzhong
 * @since 2023-09-27
 */
@Service
public class UserUserGroupService extends ServiceImpl<UserUserGroupMapper, UserUserGroup> {

    public boolean batchInsert(BatchUserUserGroupDTO batchUserUserGroupDTO) {
        String userGroupId = batchUserUserGroupDTO.getUserGroupId();
        List<String> userIds = batchUserUserGroupDTO.getUserIds();
        if (CollectionUtils.isNotEmpty(userIds)) {
            List<UserUserGroup> userUserGroups = new ArrayList<>();
            for (String userId : userIds) {
                userUserGroups.add(
                        new UserUserGroup()
                                .setUserGroupId(userGroupId)
                                .setUserId(userId)
                );
            }
            return super.saveBatch(userUserGroups);
        }
        return false;
    }

    public boolean batchDelete(BatchUserUserGroupDTO batchUserUserGroupDTO) {
        String userGroupId = batchUserUserGroupDTO.getUserGroupId();
        List<String> userIds = batchUserUserGroupDTO.getUserIds();
        if (CollectionUtils.isNotEmpty(userIds)) {
            return super.remove(
                    Wrappers.lambdaQuery(UserUserGroup.class)
                            .eq(UserUserGroup::getUserGroupId, userGroupId)
                            .in(UserUserGroup::getUserId, userIds)
            );
        }
        return false;
    }

    /**
     * 根据用户组ID删除用户关联信息
     *
     * @param userGroupIds 用户组ID
     */
    public void deleteByUserGroupIds(List<String> userGroupIds) {
        super.remove(
                Wrappers.lambdaQuery(UserUserGroup.class)
                        .in(UserUserGroup::getUserGroupId, userGroupIds)
        );
    }

    /**
     * 根据用户ID删除用户关联信息
     *
     * @param userIds 用户ID
     */
    public void deleteByUserIds(List<String> userIds) {
        super.remove(
                Wrappers.lambdaQuery(UserUserGroup.class)
                        .in(UserUserGroup::getUserId, userIds)
        );
    }
}
