package com.qcz.qmplatform.module.business.system.controller;

import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.business.system.domain.dto.BatchUserUserGroupDTO;
import com.qcz.qmplatform.module.business.system.service.UserUserGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.annotation.Resource;
/**
 * <p>
 * 用户-用户组关联 前端控制器
 * </p>
 *
 * @author quchangzhong
 * @since 2023-09-27
 */
@Controller
@RequestMapping("/system/user-user-group")
@Module("用户-用户组关联")
public class UserUserGroupController extends BaseController {

    @Resource
    private UserUserGroupService userUserGroupService;

    @PostMapping("/batchInsert")
    @ResponseBody
    @RecordLog(type = OperateType.INSERT, description = "批量新增用户-用户组关联")
    public ResponseResult<Void> batchInsert(@RequestBody BatchUserUserGroupDTO batchUserUserGroupDTO) {
        return ResponseResult.newInstance(userUserGroupService.batchInsert(batchUserUserGroupDTO));
    }

    @PostMapping("/batchDelete")
    @ResponseBody
    @RecordLog(type = OperateType.DELETE, description = "批量删除用户-用户组关联")
    public ResponseResult<Void> batchDelete(@RequestBody BatchUserUserGroupDTO batchUserUserGroupDTO) {
        return ResponseResult.newInstance(userUserGroupService.batchDelete(batchUserUserGroupDTO));
    }
}
