package com.qcz.qmplatform.module.sys.service;

import org.springframework.stereotype.Service;

import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.base.BaseService;
import com.qcz.qmplatform.module.sys.dao.DepartmentDao;
import com.qcz.qmplatform.module.sys.entity.Department;

/**
 * 部门 Service
 * @author changzhongq
 * @time 2018年6月17日 下午3:27:55
 */
@Service
@Module("部门管理")
public class DepartmentService extends BaseService<Department, DepartmentDao> {

}
