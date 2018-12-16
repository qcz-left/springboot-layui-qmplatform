package com.qcz.qmplatform.module.sys.entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色菜单 实体
 * @author changzhongq
 * @time 2018年6月19日 上午11:20:58
 */
@Table(name = "SYS_ROLE_MENU")
public class RoleMenu implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String roleMenuId;
	
	private String roleId;
	
	private String menuId;

	public String getRoleMenuId() {
		return roleMenuId;
	}

	public void setRoleMenuId(String roleMenuId) {
		this.roleMenuId = roleMenuId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
}
