package com.qcz.qmplatform.module.sys.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qcz.qmplatform.common.base.BaseController;
import com.qcz.qmplatform.common.message.PageRequest;
import com.qcz.qmplatform.common.message.PageResult;
import com.qcz.qmplatform.common.message.PageResultHelper;
import com.qcz.qmplatform.common.message.ResponseResult;
import com.qcz.qmplatform.common.utils.HttpServletUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.module.sys.entity.User;
import com.qcz.qmplatform.module.sys.form.PasswordForm;
import com.qcz.qmplatform.module.sys.service.UserService;

/**
 * 用户 Controller
 * @author changzhongq
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController<User, UserService> {

	private static final String PREFIX = "/module/sys/user/";

	@RequestMapping(value = "/userListPage", method = RequestMethod.GET)
	public String userListPage() {
		return PREFIX + "userList";
	}

	@RequestMapping(value = "/userForm", method = RequestMethod.GET)
	public String userForm() {
		return PREFIX + "userForm";
	}

	@RequestMapping(value = "/safeSettingPage", method = RequestMethod.GET)
	public String safeSettingPage() {
		return PREFIX + "safeSetting";
	}

	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	@ResponseBody
	public PageResult userList(HttpServletRequest request, PageRequest pageRequest) {

		Map<String, Object> paramMap = HttpServletUtils.parseRequestByParam(request);
		PageResultHelper.startPage(pageRequest);
		return PageResultHelper.parseResult(service.findUserList(paramMap));
	}

	/**
	 * 验证唯一的登录名
	 * @param loginName 登录名
	 */
	@RequestMapping(value = "/validateOnlyLoginName", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult validateOnlyLoginName(String loginName) {
		return service.findByLoginName(loginName);
	}

	/**
	 * 保存用户信息
	 * @param data 用户基本信息
	 * @param roleIds 角色id（多个以“,”号隔开）
	 * @return
	 */
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult saveUser(User data, String roleIds) {
		return service.saveUser(data, roleIds);
	}

	/**
	 * 更新用户状态
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/changeLockedStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult changeLockedStatus(User data) {
		return service.changeLockedStatus(data);
	}

	/**
	 * 安全设置
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/safeSetting", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult safeSetting(PasswordForm data) {
		return service.safeSetting(data);
	}

	/**
	 * 重置密码
	 * @param userId 用户id
	 * @return
	 */
	@RequestMapping(value = "/resetLoginPassword", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult resetLoginPassword(String userId) {
		return service.resetLoginPassword(userId);
	}

	/**
	 * 修改主题色
	 * @param themeColor
	 * @return
	 */
	@RequestMapping(value = "/updateThemeColor", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult updateThemeColor(String themeColor) {
		return service.updateThemeColor(SubjectUtils.getUserId(), themeColor);
	}
}
