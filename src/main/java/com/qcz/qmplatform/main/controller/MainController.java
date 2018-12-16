package com.qcz.qmplatform.main.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qcz.qmplatform.common.aop.annotation.CustomOperateLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.message.ResponseResult;
import com.qcz.qmplatform.module.sys.entity.User;

/**
 * 主要信息控制层
 * @author changzhongq
 * @time 2018年11月12日 下午8:16:43
 */
@Controller
@RequestMapping("/main")
public class MainController {

	/**
	 * 日志记录类
	 */
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	/**
	 * 返回页面路径前缀
	 */
	private static final String PATH_PREFIX = "/main";

	/**
	 * 进入到登录页面
	 */
	@RequestMapping("/loginPage")
	public String loginPage() {
		return PATH_PREFIX + "/login";
	}

	/**
	 * 进入到主页页面
	 */
	@RequestMapping("/homePage")
	public String homePage() {
		return PATH_PREFIX + "/home";
	}

	/**
	 * 登录操作
	 * @param user 前台传进参数，包含用户名和密码
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	@CustomOperateLog(type = OperateType.LOGIN)
	public ResponseResult login(User user) {
		UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginName(), user.getLoginPassword());
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.getSession().setTimeout(60 * 30 * 1000);
			subject.login(token);
			logger.debug("login success, loginName : {}", user.getLoginName());
			return ResponseResult.ok();
		} catch (AuthenticationException e) {
			logger.debug("login fail : " + e.getMessage());
			e.printStackTrace();
			return ResponseResult.error(e.getMessage());
		}
	}

	/**
	 * 登出操作，返回登录页 如果登出操作时，用户已经登出了，需做校验判断，否则程序出错，直接返回登录页
	 */
	@RequestMapping(value = "/logout")
	@CustomOperateLog(type = OperateType.LOGOUT)
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			User user = (User) subject.getPrincipal();
			subject.logout();
			logger.debug("logout : {} | {}", user.getLoginName(), user.getUserName());
		}
		return "redirect:" + PATH_PREFIX + "/loginPage";
	}
}
