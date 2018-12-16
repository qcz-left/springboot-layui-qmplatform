package com.qcz.qmplatform.module.sys.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 操作日志 实体类
 * @author changzhongq
 * @time 2018年6月22日 下午6:08:08
 */
@Table(name = "sys_operate_log")
public class SysOperateLog implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 日志ID
	 */
	@Id
	private String logId;

	/**
	 * 操作类型（-1：退出系统，0：进入系统，1：查询，2：新增，3：修改，4：删除） 需要其他类型，请自添加并注释
	 */
	private String operateType;

	/**
	 * 操作系统名称
	 */
	private String operateSystem;

	/**
	 * 操作模块
	 */
	private String operateModule;

	/**
	 * 操作人ID
	 */
	private String operateUserId;

	/**
	 * 操作人名称
	 */
	private String operateUserName;

	/**
	 * 操作时间
	 */
	private Date operateTime;

	/**
	 * 更新参数
	 */
	private String updateParams;

	/**
	 * 请求路径
	 */
	private String requestUrl;

	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * IP地址
	 */
	private String ip;

	/**
	 * 操作状态（0：失败，1：成功）
	 */
	private int operateStatus;

	@Override
	public String toString() {
		return "SysOperateLog [logId=" + logId + ", operateType=" + operateType + ", operateSystem=" + operateSystem + ", operateModule=" + operateModule + ", operateUserId=" + operateUserId
				+ ", operateUserName=" + operateUserName + ", operateTime=" + operateTime + ", updateParams=" + updateParams + ", requestUrl=" + requestUrl + ", tableName=" + tableName + ", ip=" + ip
				+ "]";
	}

	public String getOperateSystem() {
		return operateSystem;
	}

	public void setOperateSystem(String operateSystem) {
		this.operateSystem = operateSystem;
	}

	public String getOperateModule() {
		return operateModule;
	}

	public void setOperateModule(String operateModule) {
		this.operateModule = operateModule;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(int operateStatus) {
		this.operateStatus = operateStatus;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getOperateUserId() {
		return operateUserId;
	}

	public void setOperateUserId(String operateUserId) {
		this.operateUserId = operateUserId;
	}

	public String getOperateUserName() {
		return operateUserName;
	}

	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getUpdateParams() {
		return updateParams;
	}

	public void setUpdateParams(String updateParams) {
		this.updateParams = updateParams;
	}

}
