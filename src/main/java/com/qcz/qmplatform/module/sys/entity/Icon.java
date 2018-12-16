package com.qcz.qmplatform.module.sys.entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 图标
 * @author changzhongq
 * @time 2018年11月18日 下午7:24:51
 */
@Table(name = "sys_icon")
public class Icon implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	private String iconId;
	
	/**
	 * 图标编码
	 */
	private String iconCode;
	
	/**
	 * 图标名称
	 */
	private String iconName;
	
	/**
	 * 图标字体类
	 */
	private String iconFontClass;

	public String getIconId() {
		return iconId;
	}

	public void setIconId(String iconId) {
		this.iconId = iconId;
	}

	public String getIconCode() {
		return iconCode;
	}

	public void setIconCode(String iconCode) {
		this.iconCode = iconCode;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getIconFontClass() {
		return iconFontClass;
	}

	public void setIconFontClass(String iconFontClass) {
		this.iconFontClass = iconFontClass;
	}
	
}
