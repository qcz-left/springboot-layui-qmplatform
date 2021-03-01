package com.qcz.qmplatform.module.system.controller;


import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.PrivCode;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.system.pojo.MenuTree;
import com.qcz.qmplatform.module.system.pojo.Permission;
import com.qcz.qmplatform.module.system.service.MenuService;
import com.qcz.qmplatform.module.system.vo.PermissionVO;
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
@RequestMapping("/menu")
@Module("菜单管理")
public class MenuController extends BaseController {

    private static final String PATH_PREFIX = "/module/system/";

    @Autowired
    private MenuService menuService;

    @GetMapping("/menuListPage")
    public String menuListPage() {
        return PATH_PREFIX + "menuList";
    }

    @GetMapping("/menuDetailPage")
    public String menuDetailPage() {
        return PATH_PREFIX + "menuDetail";
    }

    @GetMapping("/menuTreePage")
    public String menuTreePage() {
        return PATH_PREFIX + "menuTree";
    }

    /**
     * 选择图标页面
     */
    @GetMapping("/chooseIconPage")
    public String chooseIconPage() {
        return PATH_PREFIX + "chooseIcon";
    }

    /**
     * 获取菜单列表
     *
     * @param permission 请求参数
     */
    @RequestMapping("/getMenuList")
    @ResponseBody
    public ResponseResult<List<MenuTree>> getMenuList(PermissionVO permission) {
        List<MenuTree> menuList = menuService.getMenuList(permission);
        return ResponseResult.ok(menuList);
    }

    /**
     * 获取菜单树形结构
     *
     * @param permission 请求参数
     */
    @GetMapping("/getMenuTree")
    @ResponseBody
    public ResponseResult<List<MenuTree>> getMenuTree(PermissionVO permission) {
        return ResponseResult.ok(menuService.getMenuTree(permission));
    }

    /**
     * 获取菜单/按钮信息
     *
     * @param permissionId 权限id
     */
    @GetMapping("/getPermissionOne/{permissionId}")
    @ResponseBody
    public ResponseResult<Permission> getPermissionOne(@PathVariable String permissionId) {
        return ResponseResult.ok(menuService.getPermissionById(permissionId));
    }

    /**
     * 验证权限码唯一性
     *
     * @param permissionId 权限id
     * @param code         权限码
     * @return
     */
    @GetMapping("/validatePermissionCode")
    @ResponseBody
    public ResponseResult<?> validatePermissionCode(String permissionId, String code) {
        if (!menuService.validatePermissionCode(permissionId, code)) {
            return ResponseResult.error();
        }
        return ResponseResult.ok();
    }

    /**
     * 添加权限信息
     *
     * @param permission 权限表单信息
     */
    @PostMapping("/addPermission")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_MENU_SAVE)
    @RecordLog(type = OperateType.INSERT, description = "新增权限")
    public ResponseResult<?> addPermission(@RequestBody Permission permission) {
        return savePermissionOne(permission);
    }

    /**
     * 修改菜单信息
     *
     * @param permission 权限表单信息
     */
    @PutMapping("/updatePermission")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_MENU_SAVE)
    @RecordLog(type = OperateType.UPDATE, description = "修改权限")
    public ResponseResult<?> updatePermission(@RequestBody Permission permission) {
        return savePermissionOne(permission);
    }

    /**
     * 保存菜单/按钮信息（新增或保存）
     *
     * @param permission 菜单/按钮表单信息
     */
    @PostMapping("/savePermissionOne")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_MENU_SAVE)
    public ResponseResult<?> savePermissionOne(@RequestBody Permission permission) {
        if (menuService.savePermissionOne(permission)) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
    }

    /**
     * 删除菜单信息
     *
     * @param permissionIds 菜单id
     */
    @DeleteMapping("/deletePermission")
    @RequiresPermissions(PrivCode.BTN_CODE_MENU_DELETE)
    @ResponseBody
    public ResponseResult<?> deletePermission(String permissionIds) {
        if (menuService.deletePermission(Arrays.asList(permissionIds.split(",")))) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
    }
}

