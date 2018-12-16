package com.qcz.qmplatform.module.sys.entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色 实体
 * @author changzhongq
 * @time 2018年6月18日 下午7:36:50
 */
@Table(name = "SYS_ROLE")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String roleId;
	
	private String roleName;
	
	private String roleSign;
	
	private String remark;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleSign() {
		return roleSign;
	}

	public void setRoleSign(String roleSign) {
		this.roleSign = roleSign;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
