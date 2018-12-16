package com.qcz.qmplatform.module.sys.entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 菜单 实体类
 * @author changzhongq
 * @time 2018年6月11日 下午5:30:17
 */
@Table(name = "SYS_MENU")
public class Menu implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	@Id
	private String menuId;
	
	/**
	 * 菜单名称
	 */
	private String menuName;
	
	/**
	 * 父菜单ID
	 */
	private String parentId;
	
	/**
	 * 父菜单名称
	 */
	private String parentName;
	
	/**
	 * 请求路径
	 */
	private String url;
	
	/**
	 * 菜单图标（layui图标）
	 */
	private String icon;
	
	/**
	 * 排序
	 */
	private Integer sort;

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

}
