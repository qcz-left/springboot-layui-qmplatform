package com.qcz.qmplatform.module.business.system.controller;

import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.PrivCode;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.module.business.system.domain.dto.MoveUserDTO;
import com.qcz.qmplatform.module.business.system.service.UserOrganizationService;
import jakarta.annotation.Resource;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户部门关联
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
@RestController
@RequestMapping("/user-organization")
public class UserOrganizationController {

    @Resource
    private UserOrganizationService userOrganizationService;

    /**
     * 移动用户到另一个部门
     */
    @PostMapping("moveUserToAnotherDept")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_ORG_SAVE)
    @RecordLog(type = OperateType.UPDATE, description = "移动用户")
    public ResponseResult<Void> moveUserToAnotherDept(@RequestBody MoveUserDTO moveUserDTO) {
        userOrganizationService.updateDeptByUserIds(moveUserDTO.getUserIds(), moveUserDTO.getDeptId());
        return ResponseResult.ok();
    }
}

