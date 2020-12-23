package com.qcz.qmplatform.module.system.controller;


import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.PageRequest;
import com.qcz.qmplatform.common.bean.PageResult;
import com.qcz.qmplatform.common.bean.PageResultHelper;
import com.qcz.qmplatform.common.bean.PrivCode;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.system.domain.Role;
import com.qcz.qmplatform.module.system.service.RoleService;
import com.qcz.qmplatform.module.system.vo.RolePermissionVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
@Controller
@RequestMapping("/role")
@Module("角色管理")
public class RoleController extends BaseController {

    private static final String PATH_PREFIX = "/module/system/";

    @Autowired
    private RoleService roleService;

    @GetMapping("/roleListPage")
    public String roleListPage() {
        return PATH_PREFIX + "roleList";
    }

    @GetMapping("/roleDetailPage")
    public String roleDetailPage() {
        return PATH_PREFIX + "roleDetail";
    }

    @GetMapping("/permissionTreePage")
    public String permissionTreePage() {
        return PATH_PREFIX + "permissionTree";
    }

    /**
     * 获取角色列表
     *
     * @param pageRequest 分页请求
     * @param role        请求参数
     */
    @GetMapping("/getRoleList")
    @ResponseBody
    public ResponseResult<PageResult> getRoleList(PageRequest pageRequest, Role role) {
        PageResultHelper.startPage(pageRequest);
        List<Role> roleList = roleService.getRoleList(role);
        return ResponseResult.ok(PageResultHelper.parseResult(roleList));
    }

    /**
     * 获取角色
     *
     * @param roleId 角色id
     */
    @GetMapping("/getRoleOne/{roleId}")
    @ResponseBody
    public ResponseResult<Role> getRoleOne(@PathVariable String roleId) {
        return ResponseResult.ok(roleService.getById(roleId));
    }

    /**
     * 获取角色权限
     *
     * @param roleId 角色id
     */
    @GetMapping("/getRolePermission/{roleId}")
    @ResponseBody
    public ResponseResult<List<String>> getRolePermission(@PathVariable String roleId) {
        return ResponseResult.ok(roleService.getRolePermission(roleId));
    }

    /**
     * 添加角色
     *
     * @param role 角色信息
     */
    @PostMapping("/addRole")
    @ResponseBody
    @RecordLog(type = OperateType.INSERT, description = "新增角色")
    public ResponseResult<?> addRoleOne(@RequestBody Role role) {
        return saveRoleOne(role);
    }

    /**
     * 保存角色信息（新增或保存）
     *
     * @param role 角色表单信息
     */
    @PostMapping("/saveRoleOne")
    @RequiresPermissions(PrivCode.BTN_CODE_ROLE_SAVE)
    @ResponseBody
    public ResponseResult<?> saveRoleOne(@RequestBody Role role) {
        if (roleService.saveRoleOne(role)) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
    }

    /**
     * 修改角色
     *
     * @param role 角色信息
     */
    @PutMapping("/updateRoleOne")
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "修改角色")
    public ResponseResult<?> updateRole(@RequestBody Role role) {
        return saveRoleOne(role);
    }

    /**
     * 删除角色
     *
     * @param roleIds 角色id数组
     */
    @DeleteMapping("/deleteRole")
    @RequiresPermissions(PrivCode.BTN_CODE_ROLE_DELETE)
    @ResponseBody
    @RecordLog(type = OperateType.DELETE, description = "删除角色")
    public ResponseResult<?> deleteRole(String roleIds) {
        if (roleService.deleteRole(Arrays.asList(roleIds.split(",")))) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
    }

    /**
     * 分配角色权限
     *
     * @param rolePermissionVo 角色id和权限id数组
     */
    @PostMapping("/saveRolePermission")
    @RequiresPermissions(PrivCode.BTN_CODE_ROLE_ALLOT)
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "分配角色权限")
    public ResponseResult<?> saveRolePermission(@RequestBody RolePermissionVO rolePermissionVo) {
        if (roleService.saveRolePermission(rolePermissionVo.getRoleId(), rolePermissionVo.getPermissionIds())) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
    }
}

