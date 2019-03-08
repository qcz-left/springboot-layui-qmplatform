package com.qcz.qmplatform.module.sys.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户 实体
 * @author changzhongq
 * @time 2018年6月10日 上午10:58:28
 */
@Table(name = "sys_user")
public class User implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@Id
	private String userId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 登录名
	 */
	private String loginName;

	/**
	 * 登录密码
	 */
	private String loginPassword;

	/**
	 * 性别
	 */
	private String userSex;

	/**
	 * 锁定状态（0：锁定，1：正常）
	 */
	private String locked;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建人ID
	 */
	private String createUserId;

	/**
	 * 创建人名称
	 */
	private String createUserName;

	/**
	 * 修改人ID
	 */
	private String updateUserId;

	/**
	 * 修改人名称
	 */
	private String updateUserName;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 用户图片
	 */
	private String userImage;

	/**
	 * 用户主题
	 */
	public String themeColor;

	public User() {
	}

	public User(String loginName, String loginPassword) {
		this.loginName = loginName;
		this.loginPassword = loginPassword;
	}
	
	public User(String loginName) {
		this.loginName = loginName;
	}
	
	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getThemeColor() {
		return themeColor;
	}

	public void setThemeColor(String themeColor) {
		this.themeColor = themeColor;
	}

}
