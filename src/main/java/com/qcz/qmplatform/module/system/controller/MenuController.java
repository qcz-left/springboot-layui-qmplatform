package com.qcz.qmplatform.module.system.controller;


import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.PrivCode;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.system.po.MenuTree;
import com.qcz.qmplatform.module.system.po.Permission;
import com.qcz.qmplatform.module.system.service.MenuService;
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
    public ResponseResult<List<MenuTree>> getMenuList(Permission permission) {
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
    public ResponseResult<List<MenuTree>> getMenuTree(Permission permission) {
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
     * 添加权限信息
     *
     * @param permission 权限表单信息
     */
    @PostMapping("/addPermission")
    @ResponseBody
    @RecordLog(type = OperateType.INSERT, description = "新增一个菜单或权限")
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
    @RecordLog(type = OperateType.UPDATE, description = "修改一个菜单或权限")
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
    @DeleteMapping("/deleteMenu")
    @RequiresPermissions(PrivCode.BTN_CODE_MENU_DELETE)
    @ResponseBody
    public ResponseResult<?> deleteMenu(String permissionIds) {
        if (menuService.deleteMenu(Arrays.asList(permissionIds.split(",")))) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
    }
}

