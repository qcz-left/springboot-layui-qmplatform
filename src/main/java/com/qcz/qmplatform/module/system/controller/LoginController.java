package com.qcz.qmplatform.module.system.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import com.aliyun.dingtalkcontact_1_0.models.GetUserHeaders;
import com.aliyun.dingtalkcontact_1_0.models.GetUserResponseBody;
import com.aliyun.dingtalkoauth2_1_0.models.GetUserTokenRequest;
import com.aliyun.dingtalkoauth2_1_0.models.GetUserTokenResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
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
import com.qcz.qmplatform.module.system.assist.Thirdparty;
import com.qcz.qmplatform.module.system.domain.User;
import com.qcz.qmplatform.module.system.domain.UserThirdparty;
import com.qcz.qmplatform.module.system.realm.CustomToken;
import com.qcz.qmplatform.module.system.service.IniService;
import com.qcz.qmplatform.module.system.service.MenuService;
import com.qcz.qmplatform.module.system.service.MessageService;
import com.qcz.qmplatform.module.system.service.UserService;
import com.qcz.qmplatform.module.system.service.UserThirdpartyService;
import com.qcz.qmplatform.module.system.vo.LoginFormVO;
import com.qcz.qmplatform.module.system.vo.PermissionVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.subject.Subject;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
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
    @Autowired
    UserThirdpartyService userThirdpartyService;

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
        root.put("rsaPublicKey", ConfigLoader.getRsaPublicKey());
        return "index";
    }

    /**
     * 进入到登录页面
     */
    @GetMapping("/loginPage")
    public String loginPage(Map<String, Object> root) {
        root.put("rsaPublicKey", ConfigLoader.getRsaPublicKey());
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
     * @param loginFormVO 前台传进参数，包含用户名和密码等
     */
    @PostMapping("/login")
    @ResponseBody
    @RecordLog(type = OperateType.LOGIN)
    @Retryable(value = {PSQLException.class})
    public ResponseResult<?> login(@RequestBody LoginFormVO loginFormVO, HttpServletRequest request) {
        String loginname = loginFormVO.getLoginname();
        CustomToken token = new CustomToken(loginname, loginFormVO.getPassword());

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
                String validateCode = loginFormVO.getValidateCode();
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
            // 绑定第三方账号
            String thirdparty = loginFormVO.getThirdparty();
            if (StringUtils.isNotBlank(thirdparty)) {
                UserThirdparty userThirdparty = new UserThirdparty();
                userThirdparty.setUserId(SubjectUtils.getUserId());
                userThirdparty.setThirdpartyId(loginFormVO.getThirdparyId());
                userThirdparty.setAccessType(thirdparty);
                userThirdpartyService.saveOne(userThirdparty);
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

    @PostMapping(value = "/clearLoginInfo")
    @ResponseBody
    public ResponseResult<?> clearLoginInfo() {
        SubjectUtils.removeUser();
        return ResponseResult.ok();
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

    /**
     * 与第三方绑定页面
     *
     * @param thirdparty   第三方服务商
     * @param thirdpartyId 第三方系统账号唯一ID
     */
    @RequestMapping("/noNeedLogin/bindingThirdpartyPage/{thirdparty}/{thirdpartyId}")
    public String bindingThirdpartyPage(@PathVariable("thirdparty") String thirdparty,
                                        @PathVariable("thirdpartyId") String thirdpartyId,
                                        Map<String, Object> root) {
        root.put("thirdparty", thirdparty);
        root.put("thirdpartyId", thirdpartyId);
        root.put("rsaPublicKey", ConfigLoader.getRsaPublicKey());
        return "bindingThirdparty";
    }

    /**
     * 扫码成功登录前的检查，获取第三方用户id查询系统中绑定的账号，如果没查到，则需要先进行绑定账号
     *
     * @param authCode  Oauth2 临时授权码
     * @param thirdparty 第三方服务商
     */
    @RequestMapping("/noNeedLogin/preScanCodeLoginCheck/{thirdparty}")
    public String preScanCodeLoginCheck(String authCode, @PathVariable("thirdparty") String thirdparty) throws Exception {
        String unionId = getUserInfo(authCode).getUnionId();
        UserThirdparty userThirdparty = userThirdpartyService.getUserIdByThirdparty(unionId, Enum.valueOf(Thirdparty.class, thirdparty.toUpperCase()));
        if (userThirdparty == null) {
            // 不存在绑定记录，跳转至绑定记录页面
            return "redirect:/noNeedLogin/bindingThirdpartyPage/" + thirdparty + "/" + unionId;
        }

        // 构造登录信息
        User user = userService.getById(userThirdparty.getUserId());
        CustomToken token = new CustomToken(user.getLoginname());
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        return "redirect:/";
    }

    /**
     * 钉钉：根据临时授权码获取用户信息
     */
    private GetUserResponseBody getUserInfo(String code) throws Exception {
        com.aliyun.dingtalkcontact_1_0.Client client = new com.aliyun.dingtalkcontact_1_0.Client(getDingConfig());
        GetUserHeaders getUserHeaders = new GetUserHeaders();
        getUserHeaders.xAcsDingtalkAccessToken = getAccessToken(code);
        try {
            return client.getUserWithOptions("me", getUserHeaders, new RuntimeOptions()).getBody();
        } catch (TeaException err) {
            LOGGER.error("", err);

        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            LOGGER.error("", err);
        }
        return new GetUserResponseBody();
    }

    /**
     * 钉钉：获取用户token
     */
    private String getAccessToken(String code) throws Exception {
        com.aliyun.dingtalkoauth2_1_0.Client client = new com.aliyun.dingtalkoauth2_1_0.Client(getDingConfig());
        GetUserTokenRequest getUserTokenRequest = new GetUserTokenRequest()
                .setClientId("ding87uronmn84khshi8")
                .setClientSecret("_BDntVuwA9abnbbZBH9ogmRT_XKm6OBbpevUTmunkeWrWsadcaGeBvKzmI_hKS-Y")
                .setCode(code)
                .setGrantType("authorization_code");
        try {
            GetUserTokenResponse userToken = client.getUserToken(getUserTokenRequest);
            return userToken.getBody().getAccessToken();
        } catch (TeaException err) {
            LOGGER.error("", err);
        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            LOGGER.error("", err);
        }
        return "/";
    }

    /**
     * 钉钉：使用 Token 初始化账号Client
     *
     * @return Client
     */
    private Config getDingConfig() {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return config;
    }

}
