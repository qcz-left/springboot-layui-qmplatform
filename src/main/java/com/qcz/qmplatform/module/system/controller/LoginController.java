package com.qcz.qmplatform.module.system.controller;

import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.module.system.domain.User;
import com.qcz.qmplatform.module.system.pojo.Permission;
import com.qcz.qmplatform.module.system.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@Module("身份认证")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private MenuService menuService;

    @GetMapping("/")
    public String index(Map<String, Object> root) {
        Permission permission = new Permission();
        permission.setPermissionType(1);
        permission.setDisplay(1);
        root.put("menuTree", menuService.getMenuTree(permission));
        root.put("currentUser", SubjectUtils.getUser());
        return "index";
    }

    /**
     * 进入到登录页面
     */
    @GetMapping("/loginPage")
    public String loginPage() {
        return "login";
    }

    /**
     * 进入到主页页面
     */
    @GetMapping("/homePage")
    public String homePage() {
        return "home";
    }

    /**
     * 进入到404页面
     */
    @GetMapping("/error/404")
    public String error404() {
        return "error/404";
    }

    /**
     * 进入到500页面
     */
    @GetMapping("/error/500")
    public String error500() {
        return "error/500";
    }

    /**
     * 登录操作
     *
     * @param user 前台传进参数，包含用户名和密码
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @RecordLog(type = OperateType.LOGIN)
    public ResponseResult<?> login(@RequestBody User user) {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginname(), user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.getSession().setTimeout(60 * 30 * 1000);
            subject.login(token);
            logger.debug("login success, loginName : {}", user.getLoginname());
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
    @RecordLog(type = OperateType.LOGOUT)
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            Object user = subject.getPrincipal();
            subject.logout();
            logger.debug("logout : {}", user);
        }
        return "redirect:/loginPage";
    }

}
