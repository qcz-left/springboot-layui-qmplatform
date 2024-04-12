package com.qcz.qmplatform.module.business.system.realm;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollectionUtil;
import com.qcz.qmplatform.module.business.system.mapper.UserMapper;
import com.qcz.qmplatform.module.business.system.mapper.UserRoleMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限加载接口实现类
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        String userId = (String) loginId;
        return userMapper.queryAuthoritiesByUserId(userId);
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        String userId = (String) loginId;
        return CollectionUtil.getFieldValues(userRoleMapper.getRoleByUserId(userId), "roleSign", String.class);
    }

}
