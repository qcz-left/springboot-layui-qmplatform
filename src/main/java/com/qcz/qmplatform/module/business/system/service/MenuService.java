package com.qcz.qmplatform.module.business.system.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.utils.IdUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.TreeUtils;
import com.qcz.qmplatform.module.business.system.domain.Button;
import com.qcz.qmplatform.module.business.system.domain.Menu;
import com.qcz.qmplatform.module.business.system.domain.assist.PermissionType;
import com.qcz.qmplatform.module.business.system.domain.pojo.MenuTree;
import com.qcz.qmplatform.module.business.system.domain.pojo.Permission;
import com.qcz.qmplatform.module.business.system.domain.qo.PermissionQO;
import com.qcz.qmplatform.module.business.system.mapper.MenuMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
@Service
public class MenuService extends ServiceImpl<MenuMapper, Menu> {

    @Resource
    private ButtonService buttonService;

    @Resource
    private RolePermissionService rolePermissionService;

    public List<MenuTree> getMenuTree(PermissionQO permission) {
        String permissionId = permission.getPermissionId();
        if (StringUtils.isNotBlank(permissionId)
                && permission.getPermissionType() == PermissionType.MENU.getType()) {
            permission.setNotExistsMenuIds(queryMenuIdRecursive(CollectionUtil.newArrayList(permissionId)));
        }
        return TreeUtils.buildTree(getMenuList(permission));
    }

    public List<MenuTree> getMenuList(PermissionQO permission) {
        return baseMapper.selectMenuTree(permission);
    }

    public boolean addMenuOne(Menu menu) {
        menu.setMenuId(IdUtils.getUUID());
        return save(menu);
    }

    public boolean updateMenuOne(Menu menu) {
        return updateById(menu);
    }

    public boolean saveMenuOne(Menu menu) {
        if (StringUtils.isBlank(menu.getMenuId())) {
            return addMenuOne(menu);
        }
        return updateMenuOne(menu);
    }

    public boolean savePermissionOne(Permission permission) {
        int permissionType = permission.getPermissionType();
        String permissionId = permission.getPermissionId();
        String code = permission.getCode();
        String icon = permission.getIcon();
        Integer iorder = permission.getIorder();
        String parentId = permission.getParentId();
        if (parentId == null) {
            parentId = "";
        }
        String permissionName = permission.getPermissionName();
        if (permissionType == PermissionType.MENU.getType()) {
            // 菜单
            Menu menu = new Menu();
            menu.setMenuId(permissionId);
            menu.setCode(code);
            menu.setIcon(icon);
            menu.setIorder(iorder);
            menu.setMenuName(permissionName);
            menu.setParentId(parentId);
            menu.setLinkUrl(permission.getLinkUrl());
            menu.setDisplay(permission.getDisplay());
            return saveMenuOne(menu);
        }
        // 按钮
        Button button = new Button();
        button.setButtonId(permissionId);
        button.setButtonName(permissionName);
        button.setCode(code);
        button.setIcon(icon);
        button.setIorder(iorder);
        button.setMenuId(parentId);
        return buttonService.saveButtonOne(button);
    }

    /**
     * 删除权限
     *
     * @param permissionIds 权限id 集合
     */
    public boolean deletePermission(List<String> permissionIds) {
        List<Permission> permissions = baseMapper.getPermissionByIds(permissionIds);
        List<String> menuIds = new ArrayList<>();
        List<String> buttonIds = new ArrayList<>();
        for (Permission permission : permissions) {
            String permissionId = permission.getPermissionId();
            if (permission.getPermissionType() == PermissionType.MENU.getType()) {
                menuIds.add(permissionId);
            } else {
                buttonIds.add(permissionId);
            }
        }
        if (CollectionUtil.isNotEmpty(menuIds)) {
            // 删除菜单同时删除按钮
            menuIds = queryMenuIdRecursive(menuIds);
            buttonService.deleteButtonByMenuId(menuIds);
            super.removeByIds(menuIds);
            rolePermissionService.deleteByPermissionIds(menuIds);
        }
        if (CollectionUtil.isNotEmpty(buttonIds)) {
            buttonService.removeByIds(buttonIds);
            rolePermissionService.deleteByPermissionIds(buttonIds);
        }
        return true;
    }

    /**
     * 向下递归查询出所有菜单id
     */
    public List<String> queryMenuIdRecursive(List<String> menuIds) {
        List<String> allIds = new ArrayList<>();

        if (CollectionUtil.isNotEmpty(menuIds)) {
            CollectionUtil.addAll(allIds, menuIds);

            List<String> childIds = new ArrayList<>();
            CollectionUtil.addAll(childIds, baseMapper.selectObjs(
                    Wrappers.lambdaQuery(Menu.class)
                            .in(Menu::getParentId, menuIds)
                            .select(Menu::getMenuId)
            ));

            CollectionUtil.addAll(allIds, queryMenuIdRecursive(childIds));
        }

        return allIds;
    }

    public Permission getPermissionById(String permissionId) {
        List<Permission> permissions = baseMapper.getPermissionByIds(CollectionUtil.newArrayList(permissionId));
        if (permissions != null && permissions.size() > 0) {
            return permissions.get(0);
        }
        return null;
    }

    public boolean validatePermissionCode(String permissionId, String code) {
        Assert.notBlank(code);
        return baseMapper.validatePermissionCode(permissionId, code) == 0;
    }
}
