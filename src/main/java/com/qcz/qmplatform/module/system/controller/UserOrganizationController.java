package com.qcz.qmplatform.module.system.controller;

import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.module.system.domain.dto.MoveUserDTO;
import com.qcz.qmplatform.module.system.service.UserOrganizationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    public ResponseResult<Void> moveUserToAnotherDept(@RequestBody MoveUserDTO moveUserDTO) {
        userOrganizationService.updateDeptByUserIds(moveUserDTO.getUserIds(), moveUserDTO.getDeptId());
        return ResponseResult.ok();
    }
}

