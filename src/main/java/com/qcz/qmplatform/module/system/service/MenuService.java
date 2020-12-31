package com.qcz.qmplatform.module.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.TreeUtils;
import com.qcz.qmplatform.module.system.domain.Button;
import com.qcz.qmplatform.module.system.domain.Menu;
import com.qcz.qmplatform.module.system.mapper.MenuMapper;
import com.qcz.qmplatform.module.system.pojo.MenuTree;
import com.qcz.qmplatform.module.system.pojo.Permission;
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

    public List<MenuTree> getMenuTree(Permission permission) {
        return TreeUtils.buildTree(getMenuList(permission));
    }

    public List<MenuTree> getMenuList(Permission permission) {
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
        if (permissionType == 1) {
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
     * 删除菜单及子菜单和按钮
     *
     * @param menuIds
     * @return
     */
    public boolean deleteMenu(List<String> menuIds) {
        baseMapper.deleteMenuById(menuIds);
        QueryWrapper<Button> buttonWrapper = new QueryWrapper<>();
        buttonWrapper.in("menu_id", menuIds);
        buttonService.remove(buttonWrapper);
        return true;
    }

    public Permission getPermissionById(String permissionId) {
        return baseMapper.getPermissionById(permissionId);
    }

}
