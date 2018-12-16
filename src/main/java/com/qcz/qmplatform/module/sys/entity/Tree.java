package com.qcz.qmplatform.module.sys.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 树
 * @author changzhongq
 * @time 2018年6月16日 下午9:00:00
 */
public class Tree implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String name;
	
	private String parentId;
	
	private String parentName;
	
	private List<Tree> childrens;
	
	private boolean hasParent;
	
	private boolean hasChild;

	private String icon;
	
	private String url;
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<Tree> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<Tree> childrens) {
		this.childrens = childrens;
	}

	public boolean isHasParent() {
		return hasParent;
	}

	public void setHasParent(boolean hasParent) {
		this.hasParent = hasParent;
	}

	public boolean isHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}
	
}
