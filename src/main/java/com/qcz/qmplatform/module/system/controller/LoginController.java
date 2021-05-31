package com.qcz.qmplatform.module.system.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.constant.Constant;
import com.qcz.qmplatform.common.utils.ConfigLoader;
import com.qcz.qmplatform.common.utils.HttpServletUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.module.operation.service.LoginRecordService;
import com.qcz.qmplatform.module.operation.vo.LoginStrategyVO;
import com.qcz.qmplatform.module.system.assist.PermissionType;
import com.qcz.qmplatform.module.system.domain.User;
import com.qcz.qmplatform.module.system.service.IniService;
import com.qcz.qmplatform.module.system.service.MenuService;
import com.qcz.qmplatform.module.system.service.MessageService;
import com.qcz.qmplatform.module.system.service.UserService;
import com.qcz.qmplatform.module.system.vo.PasswordVO;
import com.qcz.qmplatform.module.system.vo.PermissionVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@Module("身份认证")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MenuService menuService;
    @Autowired
    MessageService messageService;
    @Autowired
    LoginRecordService loginRecordService;
    @Autowired
    IniService iniService;
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index(Map<String, Object> root) {
        User currUser = SubjectUtils.getUser();
        PermissionVO permission = new PermissionVO();
        permission.setPermissionType(PermissionType.MENU.getType());
        permission.setDisplay(1);
        String userId = currUser.getId();
        permission.setUserId(userId);
        root.put("menuTree", menuService.getMenuTree(permission));
        root.put(Constant.CURRENT_USER_SIGN, currUser);
        root.put("maxTabs", ConfigLoader.getMaxTabs());
        root.put("messageCount", messageService.selectNoReadCount(userId));
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
     * @param passwordVO 前台传进参数，包含用户名和密码等
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @RecordLog(type = OperateType.LOGIN)
    @Retryable(value = {PSQLException.class})
    public ResponseResult<?> login(@RequestBody PasswordVO passwordVO, HttpServletRequest request) {
        String loginname = passwordVO.getLoginname();
        UsernamePasswordToken token = new UsernamePasswordToken(loginname, passwordVO.getPassword());

        String clientIp = HttpServletUtils.getIpAddress(request);

        Map<String, Object> result = new HashMap<>();
        // 当前登录错误次数
        int currLoginErrorTimes = loginRecordService.getLoginErrorTimes(loginname, clientIp);
        LoginStrategyVO loginStrategy = loginRecordService.getStrategy();
        int codeAtErrorTimes = loginStrategy.getCodeAtErrorTimes();
        int lockAtErrorTimes = loginStrategy.getLockAtErrorTimes();
        // 是否开启验证码校验
        boolean enableCode = loginStrategy.getEnable() == 1;
        if (enableCode) {
            if (currLoginErrorTimes >= lockAtErrorTimes) {
                // 账号被锁定
                userService.lockAccount(loginname);
                loginRecordService.addRemark(loginname, clientIp, "账号登录错误次数达到" + lockAtErrorTimes + "次，已被锁定");
            } else {
                String validateCode = passwordVO.getValidateCode();
                if (StringUtils.isBlank(validateCode)) {
                    if (currLoginErrorTimes >= codeAtErrorTimes) {
                        // 需要输入验证码
                        result.put("needCode", true);
                        return ResponseResult.error("该账号输入密码错误次数过多，需要输入验证码", result);
                    }
                } else if (!validateCode.equals(request.getSession().getAttribute("validateCode"))) {
                    return ResponseResult.error("验证码输入错误！");
                }
            }
        }
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            if (currLoginErrorTimes > 0) {
                // 清空当前账号错误次数
                loginRecordService.clearLoginRecord(loginname, clientIp);
            }
            LOGGER.debug("login success, loginName : {}", loginname);
            return ResponseResult.ok();
        } catch (IncorrectCredentialsException e) {
            // 密码错误
            loginRecordService.increaseErrorTimes(loginname, clientIp);
            result.put("needCode", enableCode && currLoginErrorTimes + 1 >= codeAtErrorTimes);
            return ResponseResult.error(e.getMessage(), result);
        } catch (AuthenticationException e) {
            LOGGER.error("login fail : " + e.getMessage());
            return ResponseResult.error(e.getMessage());
        }
    }

    /**
     * 登出操作，返回登录页 如果登出操作时，用户已经登出了，需做校验判断，否则程序出错，直接返回登录页
     */
    @RequestMapping(value = "/logout")
    @RecordLog(type = OperateType.LOGOUT)
    public String logout() {
        SubjectUtils.removeUser();
        return "redirect:/loginPage";
    }

    /**
     * 获取登录的图形验证码
     */
    @RequestMapping("/noNeedLogin/getLoginCode")
    public void getLoginCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(100, 38);
        captcha.setGenerator(randomGenerator);
        //图形验证码写出，可以写出到文件，也可以写出到流
        ServletOutputStream outputStream = response.getOutputStream();
        captcha.write(outputStream);
        String code = captcha.getCode();
        request.getSession().setAttribute("validateCode", code);
        outputStream.close();
    }
}
