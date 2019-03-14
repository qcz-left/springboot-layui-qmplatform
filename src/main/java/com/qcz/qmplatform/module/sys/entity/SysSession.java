package com.qcz.qmplatform.module.sys.entity;

import java.io.Serializable;

import javax.persistence.Id;

/**
 * 系统会话管理
 * @author changzhongq
 * @time 2019年3月14日 上午10:19:47
 */
public class SysSession implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String sessionId;
	
	private byte[] session;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public byte[] getSession() {
		return session;
	}

	public void setSession(byte[] session) {
		this.session = session;
	}

}
