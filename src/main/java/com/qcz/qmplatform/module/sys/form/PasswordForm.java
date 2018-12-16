package com.qcz.qmplatform.module.sys.form;

/**
 * 密码表单 用于安全设置
 * @author changzhongq
 * @time 2018年12月15日 下午2:58:51
 */
public class PasswordForm {

	/**
	 * 原密码
	 */
	public String oldLoginPassword;
	
	/**
	 * 新密码
	 */
	public String loginPassword;

	public String getOldLoginPassword() {
		return oldLoginPassword;
	}

	public void setOldLoginPassword(String oldLoginPassword) {
		this.oldLoginPassword = oldLoginPassword;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	
}
