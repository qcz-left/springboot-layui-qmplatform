package com.qcz.qmplatform.module.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qcz.qmplatform.common.base.BaseController;
import com.qcz.qmplatform.common.message.PageResult;
import com.qcz.qmplatform.common.message.PageResultHelper;
import com.qcz.qmplatform.common.message.ResponseResult;
import com.qcz.qmplatform.module.sys.entity.Menu;
import com.qcz.qmplatform.module.sys.service.MenuService;

/**
 * 菜单 Controller
 * @author changzhongq
 * @time 2018年6月11日 下午5:10:04
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController<Menu, MenuService> {

	private static final String PATH_PREFIX = "/module/sys/menu/";
	
	@RequestMapping("/menuListPage")
	public String menuListPage() {
		return PATH_PREFIX + "menuList";
	}

	@RequestMapping("/menuForm")
	public String menuForm() {
		return PATH_PREFIX + "menuForm";
	}

	@RequestMapping("/menuTreePage")
	public String menuTreePage() {
		return PATH_PREFIX + "menuTree";
	}
	
	/**
	 * 选择图标页面
	 */
	@RequestMapping("/chooseIconPage")
	public String chooseIconPage() {
		return PATH_PREFIX + "chooseIcon";
	}

	@RequestMapping("/menuList")
	@ResponseBody
	public ResponseResult menuList() {
		return ResponseResult.ok(service.findMenuList());
	}

	/**
	 * 获取菜单树信息
	 * @return json数据
	 */
	@RequestMapping("/menuTreeData")
	@ResponseBody
	public ResponseResult menuTreeData() {
		return ResponseResult.ok(service.findMenuTreesData());
	}
	
	/**
	 * 选择图标
	 */
	@RequestMapping("/chooseIcon")
	@ResponseBody
	public ResponseResult chooseIcon() {
		return ResponseResult.ok(service.chooseIcon());
	}
	
	/**
	 * 根据角色id查询树形结构的菜单数据
	 * @param roleId
	 */
	@RequestMapping("/menuTreeWithStatus")
	@ResponseBody
	public PageResult menuTreeWithStatus(String roleId) {
		return PageResultHelper.parseResult(service.getMenuTreeWithStatus(roleId));
	}

}
