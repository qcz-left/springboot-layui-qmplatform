package com.qcz.qmplatform.module.sys.form;

import java.util.Date;

/**
 * 用户表单
 * @author changzhongq
 * @time 2018年12月1日 下午7:41:28
 */
public class UserForm {

	/**
	 * 用户ID
	 */
	public String userId;

	/**
	 * 用户名
	 */
	public String userName;

	/**
	 * 登录名
	 */
	public String loginName;

	/**
	 * 登录密码
	 */
	public String loginPassword;

	/**
	 * 性别
	 */
	public String userSex;

	/**
	 * 锁定状态（0：锁定，1：正常）
	 */
	public String locked;

	/**
	 * 备注
	 */
	public String remark;

	/**
	 * 创建人ID
	 */
	public String createUserId;

	/**
	 * 创建人名称
	 */
	public String createUserName;

	/**
	 * 修改人ID
	 */
	public String updateUserId;

	/**
	 * 修改人名称
	 */
	public String updateUserName;

	/**
	 * 创建时间
	 */
	public Date createTime;

	/**
	 * 更新时间
	 */
	public Date updateTime;

	/**
	 * 用户图片
	 */
	public String userImage;
	
	public String[] roleIds;
}
