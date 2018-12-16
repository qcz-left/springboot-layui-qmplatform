package com.qcz.qmplatform.module.sys.entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户-角色对应 实体
 * @author changzhongq
 */
@Table(name = "SYS_USER_ROLE")
public class UserRole implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String userRoleId;
	
	private String roleId;
	
	private String userId;

	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
