package com.qcz.qmplatform.module.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.base.BaseService;
import com.qcz.qmplatform.common.message.ResponseResult;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.module.common.CommonService;
import com.qcz.qmplatform.module.sys.dao.IconDao;
import com.qcz.qmplatform.module.sys.dao.MenuDao;
import com.qcz.qmplatform.module.sys.entity.Icon;
import com.qcz.qmplatform.module.sys.entity.Menu;
import com.qcz.qmplatform.module.sys.entity.RoleMenu;
import com.qcz.qmplatform.module.sys.entity.Tree;

/**
 * 菜单服务
 * @author changzhongq
 * @time 2018年10月20日 下午10:19:41
 */
@Service
@Module("菜单管理")
public class MenuService extends BaseService<Menu, MenuDao> {

    @Autowired
    private IconDao iconDao;

    public Set<String> listPerms(String userId) {
        return null;
    }

    public List<Map<String, Object>> findMenuList() {
        return mapper.menuList();
    }

    /**
     * 首页菜单树数据
     * @return
     */
    public List<Tree> findMenuTreesData() {
        List<Tree> menus = mapper.menuTreesData(SubjectUtils.getUserId());
        List<Tree> mostHeightNodes = new ArrayList<Tree>();// 顶级节点
        for (Tree menu : menus) {
            if ("0".equals(menu.getParentId())) {
                Tree tree = menu;
                tree.setHasParent(false);
                mostHeightNodes.add(tree);
            }
            // 如果没有设置菜单图标，展示默认菜单图标
            if (StringUtils.isEmpty(menu.getIcon())) {
                menu.setIcon("layui-icon layui-icon-template-1");
            }
        }
        return buildTree(mostHeightNodes, menus);
    }

    public List<Tree> buildTree(List<Tree> trees, List<Tree> menus) {
        for (Tree tree : trees) {
            String parentId = tree.getId();
            List<Tree> childrens = new ArrayList<Tree>();
            for (Tree menu : menus) {
                if (parentId.equals(menu.getParentId())) {
                    childrens.add(menu);
                    tree.setHasChild(true);
                }
            }
            // 如果有子节点，执行递归查询
            if (childrens.size() > 0) {
                childrens = buildTree(childrens, menus);
            }
            tree.setChildrens(childrens);
        }
        return trees;
    }

    /**
     * 角色设置菜单权限
     * @param roleId  角色id
     * @param menuIds 菜单id数组
     */
    @Transactional
    public ResponseResult bindMenu(String roleId, String[] menuIds) {
        if (StringUtils.isBlank(roleId)) {
            throw new NullPointerException("传入的roleId为空！");
        }
        CommonService.deleteBy(RoleMenu.class, "roleId", roleId);
        List<RoleMenu> roleMenus = new ArrayList<RoleMenu>();
        for (String menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleMenuId(StringUtils.getUUID());
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenus.add(roleMenu);
        }
        CommonService.batchInsert(roleMenus.toArray());
        return ResponseResult.ok("保存成功！", null);
    }

    public List<Icon> chooseIcon() {
        return iconDao.selectAll();
    }

    public List<Map<String, Object>> getMenuTreeWithStatus(String roleId) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Map<String, Object>> menuTreeData = mapper.getMenuTreeWithStatus(roleId);
        for (Map<String, Object> map : menuTreeData) {
            if ("0".equals(map.get("parentId"))) {
                map.put("isLast", false);
                map.put("level", 1);
                result.add(map);
            }
        }
        return buildTreeWithStatus(result, menuTreeData, 2);
    }

    public List<Map<String, Object>> buildTreeWithStatus(List<Map<String, Object>> lastNodes, List<Map<String, Object>> menus, int level) {
        for (Map<String, Object> lastNode : lastNodes) {
            Map<String, Object> checkArrMap = new HashMap<>();
            checkArrMap.put("type", 0);
            checkArrMap.put("isChecked", lastNode.get("checked"));
            lastNode.put("checkArr", checkArrMap);
            String parentId = (String) lastNode.get("id");
            List<Map<String, Object>> childrens = new ArrayList<Map<String, Object>>();
            for (Map<String, Object> menu : menus) {
                if (parentId.equals(menu.get("parentId"))) {
                    Map<String, Object> checkMap = new HashMap<>();
                    checkMap.put("type", 0);
                    checkMap.put("isChecked", menu.get("checked"));
                    menu.put("checkArr", checkMap);
                    menu.put("level", level);
                    childrens.add(menu);
                }
            }
            // 如果有子节点，执行递归查询
            if (childrens.size() > 0) {
                lastNode.put("isLast", false);
                childrens = buildTreeWithStatus(childrens, menus, ++level);
            } else {
                lastNode.put("isLast", true);
            }
            lastNode.put("children", childrens);
        }
        return lastNodes;
    }

}
