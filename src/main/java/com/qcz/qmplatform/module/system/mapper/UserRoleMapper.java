package com.qcz.qmplatform.module.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcz.qmplatform.module.system.domain.Role;
import com.qcz.qmplatform.module.system.domain.UserRole;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    List<Role> getRoleByUserId(String userId);
}
