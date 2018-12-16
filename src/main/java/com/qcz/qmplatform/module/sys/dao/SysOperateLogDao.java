package com.qcz.qmplatform.module.sys.dao;

import java.util.List;
import java.util.Map;

import com.qcz.qmplatform.common.base.BaseDao;
import com.qcz.qmplatform.module.sys.entity.SysOperateLog;

/**
 * 操作日志Mapper
 * @author changzhongq
 * @time 2018年8月7日 下午3:31:05
 */
public interface SysOperateLogDao extends BaseDao<SysOperateLog> {

	List<SysOperateLog> findSysOperateLogList(Map<String, Object> paramMap);

	List<Map<String, Object>> statisticsByOperateType();
	
	List<Map<String, Object>> statisticsByUser();

}
