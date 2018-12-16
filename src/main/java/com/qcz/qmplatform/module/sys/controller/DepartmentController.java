package com.qcz.qmplatform.module.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qcz.qmplatform.common.base.BaseController;
import com.qcz.qmplatform.module.sys.entity.Department;
import com.qcz.qmplatform.module.sys.service.DepartmentService;

/**
 * 部门 Controller
 * @author changzhongq
 */
@Controller
@RequestMapping("/department")
public class DepartmentController extends BaseController<Department, DepartmentService> {

	private static final String PREFIX = "/module/sys/department/";

	@RequestMapping("/departmentListPage")
	public String departmentListPage() {
		return PREFIX + "departmentList";
	}

	@RequestMapping("/departmentForm")
	public String departmentForm() {
		return PREFIX + "departmentForm";
	}

	@RequestMapping("/departmentTreePage")
	public String departmentTreePage() {
		return PREFIX + "departmentTree";
	}
	
}
