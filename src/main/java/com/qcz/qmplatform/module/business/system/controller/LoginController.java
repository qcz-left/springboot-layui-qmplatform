package com.qcz.qmplatform.module.business.system.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.constant.Constant;
import com.qcz.qmplatform.common.exception.BusinessException;
import com.qcz.qmplatform.common.utils.ConfigLoader;
import com.qcz.qmplatform.common.utils.JSONUtils;
import com.qcz.qmplatform.common.utils.SecureUtils;
import com.qcz.qmplatform.common.utils.ServletUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.module.business.operation.domain.vo.LoginStrategyVO;
import com.qcz.qmplatform.module.business.operation.service.LoginRecordService;
import com.qcz.qmplatform.module.business.operation.service.LoginSettingService;
import com.qcz.qmplatform.module.business.system.domain.ThirdpartyApp;
import com.qcz.qmplatform.module.business.system.domain.User;
import com.qcz.qmplatform.module.business.system.domain.UserThirdparty;
import com.qcz.qmplatform.module.business.system.domain.assist.PermissionType;
import com.qcz.qmplatform.module.business.system.domain.assist.Thirdparty;
import com.qcz.qmplatform.module.business.system.domain.dto.LoginDTO;
import com.qcz.qmplatform.module.business.system.domain.pojo.DingTalkUserAccessToken;
import com.qcz.qmplatform.module.business.system.domain.pojo.DingTalkUserInfo;
import com.qcz.qmplatform.module.business.system.domain.qo.PermissionQO;
import com.qcz.qmplatform.module.business.system.domain.vo.UserVO;
import com.qcz.qmplatform.module.business.system.service.MenuService;
import com.qcz.qmplatform.module.business.system.service.MessageService;
import com.qcz.qmplatform.module.business.system.service.ThirdpartyAppService;
import com.qcz.qmplatform.module.business.system.service.UserService;
import com.qcz.qmplatform.module.business.system.service.UserThirdpartyService;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@Module("身份认证")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Resource
    MenuService menuService;
    @Resource
    MessageService messageService;
    @Resource
    LoginRecordService loginRecordService;
    @Resource
    UserService userService;
    @Resource
    UserThirdpartyService userThirdpartyService;
    @Resource
    ThirdpartyAppService thirdpartyAppService;
    @Resource
    LoginSettingService loginSettingService;

    @GetMapping("/")
    public String index(Map<String, Object> root) {
        User currUser = SubjectUtils.getUser();
        PermissionQO permission = new PermissionQO();
        permission.setPermissionType(PermissionType.MENU.getType());
        permission.setDisplay(1);
        String userId = currUser.getId();
        permission.setUserId(userId);
        root.put("menuTree", menuService.getMenuTree(permission));
        root.put(Constant.CURRENT_USER_SIGN, currUser);
        root.put("maxTabs", ConfigLoader.getMaxTabs());
        root.put("messageCount", messageService.selectNoReadCount(userId));
        root.put("rsaPublicKey", ConfigLoader.getRsaPublicKey());
        root.put("systemTitle", loginSettingService.get().getSystemChineseTitle());
        return "index";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/nnl/loginAgain")
    public String loginAgain(RedirectAttributes redirectAttributes, int code) {
        redirectAttributes.addFlashAttribute("code", code);
        return "redirect:/loginPage";
    }

    /**
     * 进入到登录页面
     */
    @GetMapping("/loginPage")
    public String loginPage(Map<String, Object> root) {
        root.put("rsaPublicKey", ConfigLoader.getRsaPublicKey());
        ThirdpartyApp dingTalkCode = thirdpartyAppService.getByName("dingtalk-code");
        if (dingTalkCode != null) {
            root.put("dingTalkConfigAppKey", dingTalkCode.getAppKey());
        }
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
     * @param loginDTO 前台传进参数，包含用户名和密码等
     */
    @PostMapping("/login")
    @ResponseBody
    @RecordLog(type = OperateType.LOGIN)
    @Retryable(value = {PSQLException.class})
    public ResponseResult<?> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        String loginname = loginDTO.getLoginname();
        String password = loginDTO.getPassword();
        String clientIp = ServletUtils.getIpAddress(request);

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
                String validateCode = loginDTO.getValidateCode();
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
        UserVO user = userService.queryUserByName(loginname);
        // 账号不存在
        if (user == null) {
            throw new BusinessException("不存在该账号");
        }
        // 账号被锁定
        if (user.getLocked() == 1) {
            throw new BusinessException("账号已被锁定,请联系管理员");
        }
        // 密码错误
        if (!SecureUtils.accountCheck(password, user.getPassword())) {
            loginRecordService.increaseErrorTimes(loginname, clientIp);
            result.put("needCode", enableCode && currLoginErrorTimes + 1 >= codeAtErrorTimes);
            return ResponseResult.error("密码错误", result);
        }

        StpUtil.login(user.getId());
        SubjectUtils.setUser(user);

        if (currLoginErrorTimes > 0) {
            // 清空当前账号错误次数
            loginRecordService.clearLoginRecord(loginname, clientIp);
        }
        // 绑定第三方账号
        String thirdparty = loginDTO.getThirdparty();
        if (StringUtils.isNotBlank(thirdparty)) {
            UserThirdparty userThirdparty = new UserThirdparty();
            userThirdparty.setUserId(SubjectUtils.getUserId());
            userThirdparty.setThirdpartyId(loginDTO.getThirdparyId());
            userThirdparty.setAccessType(thirdparty);
            userThirdpartyService.saveOne(userThirdparty);
        }
        LOGGER.debug("login success, loginName : {}", loginname);
        return ResponseResult.ok();
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
    public ResponseResult<Void> clearLoginInfo() {
        SubjectUtils.removeUser();
        return ResponseResult.ok();
    }

    /**
     * 获取登录的图形验证码
     */
    @RequestMapping("/nnl/getLoginCode")
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
    @RequestMapping("/nnl/bindingThirdpartyPage/{thirdparty}/{thirdpartyId}")
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
     * @param authCode   Oauth2 临时授权码
     * @param thirdparty 第三方服务商
     */
    @RequestMapping("/nnl/preScanCodeLoginCheck/{thirdparty}")
    public String preScanCodeLoginCheck(String authCode, @PathVariable("thirdparty") String thirdparty) throws Exception {
        String unionId = getUserInfo(authCode).getUnionId();
        UserThirdparty userThirdparty = userThirdpartyService.getUserIdByThirdparty(unionId, Enum.valueOf(Thirdparty.class, thirdparty.toUpperCase()));
        if (userThirdparty == null) {
            // 不存在绑定记录，跳转至绑定记录页面
            return "redirect:/nnl/bindingThirdpartyPage/" + thirdparty + "/" + unionId;
        }

        // 构造登录信息
        User user = userService.getById(userThirdparty.getUserId());
        StpUtil.login(user.getId());
        return "redirect:/";
    }

    /**
     * 钉钉：根据临时授权码获取用户信息
     */
    private DingTalkUserInfo getUserInfo(String code) {
        String httpUrl = "https://api.dingtalk.com/v1.0/contact/users/me";
        HttpRequest request = HttpUtil.createRequest(Method.GET, httpUrl);
        request.header(Header.CONTENT_TYPE, ContentType.JSON.getValue());
        request.header("x-acs-dingtalk-access-token", getAccessToken(code));
        HttpResponse response = request.execute();
        String body = response.body();
        LOGGER.info("get dingtalk user :" + JSONUtils.formatJsonStr(body));
        return JSONUtils.toBean(body, DingTalkUserInfo.class);
    }

    /**
     * 钉钉：获取用户token
     */
    private String getAccessToken(String code) {
        ThirdpartyApp thirdpartyApp = thirdpartyAppService.getByName("dingtalk-code");
        if (thirdpartyApp == null) {
            throw new BusinessException("钉钉扫码参数未设置，请联系系统管理员设置相应的参数！");
        }
        String httpUrl = "https://api.dingtalk.com/v1.0/oauth2/userAccessToken";
        HttpRequest request = HttpUtil.createRequest(Method.POST, httpUrl);
        request.header(Header.CONTENT_TYPE, ContentType.JSON.getValue());
        Map<String, Object> bodyParams = new HashMap<>();
        bodyParams.put("clientId", thirdpartyApp.getAppKey());
        bodyParams.put("clientSecret", thirdpartyApp.getAppSecret());
        bodyParams.put("code", code);
        bodyParams.put("grantType", "authorization_code");
        request.body(JSONUtils.toJsonStr(bodyParams));

        HttpResponse response = request.execute();
        String body = response.body();
        LOGGER.info("get dingtalk accessToken :" + JSONUtils.formatJsonStr(body));

        return JSONUtils.toBean(body, DingTalkUserAccessToken.class).getAccessToken();
    }

}
