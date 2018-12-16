package com.qcz.qmplatform.module.sys.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qcz.qmplatform.common.base.BaseController;
import com.qcz.qmplatform.common.message.PageRequest;
import com.qcz.qmplatform.common.message.PageResult;
import com.qcz.qmplatform.common.message.PageResultHelper;
import com.qcz.qmplatform.common.message.ResponseResult;
import com.qcz.qmplatform.common.utils.HttpServletUtils;
import com.qcz.qmplatform.module.sys.entity.SysOperateLog;
import com.qcz.qmplatform.module.sys.service.SysOperateLogService;

/**
 * 操作日志 Controller
 * @author changzhongq
 */
@Controller
@RequestMapping("/sysOperateLog")
public class SysOperateLogController extends BaseController<SysOperateLog, SysOperateLogService> {

	private static final String PATH_PREFIX = "/module/sys/sysOperateLog/";

	@RequestMapping("/sysOperateLogListPage")
	public String sysOperateLogListPage() {
		return PATH_PREFIX + "sysOperateLogList";
	}

	@RequestMapping("/sysOperateLogForm")
	public String sysOperateLogForm() {
		return PATH_PREFIX + "sysOperateLogForm";
	}

	@RequestMapping("/sysOperateLogList")
	@ResponseBody
	public PageResult sysOperateLogList(HttpServletRequest request, PageRequest pageRequest) {

		Map<String, Object> paramMap = HttpServletUtils.parseRequestByParam(request);
		PageResultHelper.startPage(pageRequest);
		return PageResultHelper.parseResult(service.findSysOperateLogList(paramMap));
	}
	
	/**
	 * 根据操作类型统计日志
	 * @return
	 */
	@RequestMapping("/statisticsOperateLog")
	@ResponseBody
	public ResponseResult statisticsByOperateType() {
		return ResponseResult.ok(service.statisticsByOperateType());
	}
	
	/**
	 * 根据用户统计访问数量
	 * @return
	 */
	@RequestMapping("/statisticsVisitUser")
	@ResponseBody
	public ResponseResult statisticsByUser() {
		return ResponseResult.ok(service.statisticsByUser());
	}
}
