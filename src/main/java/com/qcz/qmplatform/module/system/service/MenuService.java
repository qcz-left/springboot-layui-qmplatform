package com.qcz.qmplatform.module.system.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.TreeUtils;
import com.qcz.qmplatform.module.system.assist.PermissionType;
import com.qcz.qmplatform.module.system.domain.Button;
import com.qcz.qmplatform.module.system.domain.Menu;
import com.qcz.qmplatform.module.system.mapper.MenuMapper;
import com.qcz.qmplatform.module.system.pojo.MenuTree;
import com.qcz.qmplatform.module.system.pojo.Permission;
import com.qcz.qmplatform.module.system.vo.PermissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private ButtonService buttonService;

    public List<MenuTree> getMenuTree(PermissionVO permission) {
        return TreeUtils.buildTree(getMenuList(permission));
    }

    public List<MenuTree> getMenuList(PermissionVO permission) {
        return baseMapper.selectMenuTree(permission);
    }

    public boolean addMenuOne(Menu menu) {
        menu.setMenuId(StringUtils.uuid());
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
        int iorder = permission.getIorder();
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
        for (Permission permission : permissions) {
            String permissionId = permission.getPermissionId();
            if (permission.getPermissionType() == PermissionType.MENU.getType()) {
                // 删除菜单同时删除按钮
                buttonService.deleteButtonByMenuId(permissionId);

                baseMapper.deleteMenuById(permissionId);
            } else {
                buttonService.removeById(permissionId);
            }
        }
        return true;
    }

    public Permission getPermissionById(String permissionId) {
        List<Permission> permissions = baseMapper.getPermissionByIds(CollectionUtil.newArrayList(permissionId));
        if (permissions != null && permissions.size() > 0) {
            return permissions.get(0);
        }
        return null;
    }

}
