package com.qcz.qmplatform.module.sys.entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 部门 实体
 * @author changzhongq
 * @time 2018年6月17日 下午3:25:43
 */
@Table(name = "SYS_DEPARTMENT")
public class Department implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String departmentId;
	
	private String departmentName;
	
	private String parentId;
	
	private String parentName;
	
	private String remark;

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
