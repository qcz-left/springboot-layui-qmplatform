package com.qcz.qmplatform.module.sys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.base.BaseService;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.common.CommonService;
import com.qcz.qmplatform.module.sys.dao.RoleDao;
import com.qcz.qmplatform.module.sys.entity.Role;
import com.qcz.qmplatform.module.sys.entity.UserRole;
import com.qcz.qmplatform.module.sys.form.RoleForm;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 角色 Service
 * @author changzhongq
 */
@Service
@Module("角色管理")
public class RoleService extends BaseService<Role, RoleDao> {

	@Autowired
	private MenuService menuService;

	/**
	 * 绑定角色，先删除所有相关角色，在绑定所选角色
	 * @param userId 用户ID
	 * @param roles 要绑定的角色ID
	 */
	@Transactional
	public void bindRole(String userId, String[] roles) {
		Assert.notNull(userId, "The userId must not be null");
		CommonService.deleteBy(UserRole.class, "userId", userId);
		List<UserRole> userRoles = new ArrayList<UserRole>();
		for (String roleId : roles) {
			UserRole userRole = new UserRole();
			userRole.setUserRoleId(StringUtils.getUUID());
			userRole.setUserId(userId);
			userRole.setRoleId(roleId);
			userRoles.add(userRole);
		}
		CommonService.batchInsert(userRoles.toArray());
	}

	/**
	 * 根据用户id获取用户角色关系
	 * @param userId 用户ID
	 */
	public List<RoleForm> getRoleByUserId(String userId) {
		return mapper.getRoleByUserId(userId);
	}

	public List<Role> findRoleList(Map<String, Object> paramMap) {
		Example example = new Example(Role.class);
		Criteria criteria = example.createCriteria();
		String roleName = (String) paramMap.get("roleName");
		if (!StringUtils.isBlank(roleName)) {
			criteria.andLike("roleName", "%" + roleName + "%");
		}
		return mapper.selectByExample(example);
	}

	public List<RoleForm> findRoleListWithStatus(Map<String, Object> paramMap) {
		return mapper.findRoleListWithStatus(paramMap);
	}

	@Transactional
	public Object saveRole(Role data, String menuIds) {
		String roleId = data.getRoleId();
		if (StringUtils.isBlank(roleId)) {
			roleId = StringUtils.getUUID();
			data.setRoleId(roleId);
		}
		Object result = save(data);
		if (!StringUtils.isBlank(menuIds)) {
			menuService.bindMenu(roleId, menuIds.split(","));
		}
		return result;
	}

}
