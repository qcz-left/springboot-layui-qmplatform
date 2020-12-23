package com.qcz.qmplatform.module.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcz.qmplatform.module.system.domain.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> getPermissionIdsByRoleId(String roleId);
}
