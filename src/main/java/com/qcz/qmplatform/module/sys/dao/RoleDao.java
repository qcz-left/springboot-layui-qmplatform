package com.qcz.qmplatform.module.sys.dao;

import java.util.List;
import java.util.Map;

import com.qcz.qmplatform.common.base.BaseDao;
import com.qcz.qmplatform.module.sys.entity.Role;
import com.qcz.qmplatform.module.sys.form.RoleForm;

/**
 * 角色 数据层
 * @author changzhongq
 */
public interface RoleDao extends BaseDao<Role> {

	List<RoleForm> getRoleByUserId(String userId);

	List<RoleForm> findRoleListWithStatus(Map<String, Object> paramMap);

}
