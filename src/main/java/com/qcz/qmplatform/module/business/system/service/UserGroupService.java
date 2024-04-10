package com.qcz.qmplatform.module.business.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.utils.IdUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.TreeUtils;
import com.qcz.qmplatform.module.business.system.domain.UserGroup;
import com.qcz.qmplatform.module.business.system.domain.pojo.UserGroupTree;
import com.qcz.qmplatform.module.business.system.domain.vo.UserGroupVO;
import com.qcz.qmplatform.module.business.system.mapper.UserGroupMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 用户组 服务实现类
 * </p>
 *
 * @author quchangzhong
 * @since 2023-08-28
 */
@Service
public class UserGroupService extends ServiceImpl<UserGroupMapper, UserGroup> {

    @Resource
    private UserUserGroupService userUserGroupService;

    /**
     * 获取用户组树形结构数据
     */
    public List<UserGroupTree> getUserGroupTree() {
        List<UserGroup> userGroupList = this.list();
        return TreeUtils.buildTree(BeanUtil.copyToList(userGroupList, UserGroupTree.class));
    }

    @Override
    public boolean save(UserGroup entity) {
        if (StringUtils.isBlank(entity.getId())) {
            entity.setId(IdUtils.getUUID());
        }
        return super.save(entity);
    }

    @Override
    public UserGroupVO getById(Serializable id) {
        UserGroup userGroup = super.getById(id);
        UserGroupVO userGroupVO = new UserGroupVO();
        BeanUtil.copyProperties(userGroup, userGroupVO);

        String parentId = userGroup.getParentId();
        if (StringUtils.isNotBlank(parentId)) {
            UserGroup parentUserGroup = super.getById(parentId);
            userGroupVO.setParentName(parentUserGroup.getName());
        }
        return userGroupVO;
    }

    public boolean removeByIds(List<String> idList) {
        List<String> userGroupIds = queryUserGroupIdRecursive(idList);
        // 删除用户关联
        userUserGroupService.deleteByUserGroupIds(userGroupIds);
        return super.removeByIds(userGroupIds);
    }

    /**
     * 向下递归查询出所有用户组id
     */
    public List<String> queryUserGroupIdRecursive(Collection<? extends Serializable> userGroupId) {
        List<String> allIds = new ArrayList<>();

        if (CollectionUtil.isNotEmpty(userGroupId)) {
            CollectionUtil.addAll(allIds, userGroupId);

            List<String> childIds = new ArrayList<>();
            CollectionUtil.addAll(childIds, baseMapper.selectObjs(
                    Wrappers.lambdaQuery(UserGroup.class)
                            .in(UserGroup::getParentId, userGroupId)
                            .select(UserGroup::getId)
            ));

            CollectionUtil.addAll(allIds, queryUserGroupIdRecursive(childIds));
        }

        return allIds;
    }
}
