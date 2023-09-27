package com.qcz.qmplatform.module.business.system.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.TreeUtils;
import com.qcz.qmplatform.module.business.system.domain.UserGroup;
import com.qcz.qmplatform.module.business.system.domain.pojo.UserGroupTree;
import com.qcz.qmplatform.module.business.system.domain.vo.UserGroupVO;
import com.qcz.qmplatform.module.business.system.mapper.UserGroupMapper;
import org.springframework.stereotype.Service;

import java.io.Serializable;
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

    /**
     * 获取用户组树形结构数据
     */
    public List<UserGroupTree> getUserGroupTree() {
        List<UserGroup> userGroupList = this.list();
        return TreeUtils.buildTree(BeanUtil.copyToList(userGroupList, UserGroupTree.class));
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
}
