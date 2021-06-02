package com.qcz.qmplatform.module.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.PageRequest;
import com.qcz.qmplatform.common.bean.PageResult;
import com.qcz.qmplatform.common.bean.PageResultHelper;
import com.qcz.qmplatform.common.bean.PrivCode;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.constant.Constant;
import com.qcz.qmplatform.common.utils.CacheUtils;
import com.qcz.qmplatform.common.utils.FileUtils;
import com.qcz.qmplatform.common.utils.SecureUtils;
import com.qcz.qmplatform.common.utils.SmsUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.notify.NotifyServiceFactory;
import com.qcz.qmplatform.module.notify.bean.SmsConfig;
import com.qcz.qmplatform.module.notify.bean.TemplateType;
import com.qcz.qmplatform.module.notify.vo.SmsConfigVO;
import com.qcz.qmplatform.module.system.domain.User;
import com.qcz.qmplatform.module.system.qo.UserQO;
import com.qcz.qmplatform.module.system.service.UserService;
import com.qcz.qmplatform.module.system.vo.CurrentUserInfoVO;
import com.qcz.qmplatform.module.system.vo.PasswordVO;
import com.qcz.qmplatform.module.system.vo.UserVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户前端控制器
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
@Controller
@RequestMapping("/user")
@Module("用户管理")
public class UserController extends BaseController {

    private static final String PREFIX = "/module/system/";

    @Autowired
    private UserService userService;

    @GetMapping("/userListPage")
    public String userListPage() {
        return PREFIX + "userList";
    }

    @GetMapping("/userDetail")
    public String userDetail() {
        return PREFIX + "userDetail";
    }

    @GetMapping("/safeSettingPage")
    public String safeSettingPage() {
        return PREFIX + "safeSetting";
    }

    @GetMapping("/uploadUserImgPage")
    public String uploadUserImgPage() {
        return PREFIX + "uploadUserImg";
    }

    /**
     * 个人基本资料
     */
    @GetMapping("/personalBasicInfoPage")
    public String personalBasicInfoPage() {
        return PREFIX + "personalBasicInfo";
    }

    /**
     * 找回密码
     */
    @GetMapping("/noNeedLogin/retrievePasswordPage")
    public String retrievePasswordPage() {
        return PREFIX + "retrievePassword";
    }

    @GetMapping("/getUser/{userId}")
    @ResponseBody
    public UserVO getUserInfo(@PathVariable String userId) {
        return userService.getUserOne(userId);
    }

    @RequestMapping("/getUserList")
    @ResponseBody
    public ResponseResult<PageResult> getUserList(PageRequest pageRequest, UserQO user, boolean export) {
        if (!export) {
            PageResultHelper.startPage(pageRequest);
        }
        String organizationIdsStr = user.getOrganizationIdsStr();
        if (StringUtils.isNotBlank(organizationIdsStr)) {
            user.setOrganizationIds(CollectionUtil.newArrayList(organizationIdsStr.split(",")));
        }
        return ResponseResult.ok(PageResultHelper.parseResult(userService.getUserList(user)));
    }

    @Override
    protected void exportFormat(List rows) {

    }

    /**
     * 校验登录名唯一
     *
     * @param loginname 登录名
     */
    @GetMapping("/validateLoginName")
    @ResponseBody
    public ResponseResult<?> validateLoginName(String loginname, String userId) {
        if (!userService.validateLoginName(loginname, userId)) {
            return ResponseResult.error("登录名已存在！", loginname);
        }
        return ResponseResult.ok();
    }

    @GetMapping("/noNeedLogin/queryUserByName")
    @ResponseBody
    public ResponseResult<UserVO> queryUserByName(String name) {
        return ResponseResult.ok(userService.queryUserByName(name));
    }

    @GetMapping("/noNeedLogin/queryPhoneByName")
    @ResponseBody
    public ResponseResult<?> queryPhoneByName(String name) {
        UserVO userVO = userService.queryUserByName(name);
        if (userVO == null) {
            return ResponseResult.error("该账号信息不存在！");
        }
        String phone = userVO.getPhone();
        if (StringUtils.isBlank(phone)) {
            return ResponseResult.error("该账号未设置手机号！");
        }
        return ResponseResult.ok("", phone);
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/getCurrentUserInfo")
    @ResponseBody
    public ResponseResult<CurrentUserInfoVO> getCurrentUserInfo() {
        return ResponseResult.ok(userService.getCurrentUserInfo());
    }

    @PostMapping("/addUser")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_USER_SAVE)
    @RecordLog(type = OperateType.INSERT, description = "新增用户")
    public ResponseResult<?> addUser(@Valid @RequestBody UserVO user) {
        if (userService.insertUser(user)) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
    }

    @PutMapping("/updateUser")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_USER_SAVE)
    @RecordLog(type = OperateType.UPDATE, description = "修改用户")
    public ResponseResult<?> updateUser(@Valid @RequestBody UserVO user) {
        if (userService.updateUser(user)) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
    }

    /**
     * 保存当前用户基本资料
     *
     * @param user 需要修改的用户信息
     */
    @PutMapping("/saveCurrentUserInfo")
    @RequiresPermissions(PrivCode.BTN_CODE_USER_SAVE)
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "修改当前用户基本资料")
    public ResponseResult<?> saveCurrentUserInfo(@Valid @RequestBody CurrentUserInfoVO user) {
        if (userService.saveCurrentUserInfo(user)) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
    }

    @PutMapping("/noNeedLogin/changeUserPwd")
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "找回密码")
    public ResponseResult<?> changeUserPwd(@Valid @RequestBody PasswordVO passwordVO) {
        UserVO user = userService.queryUserByName(passwordVO.getLoginname());

        String cacheCode = CacheUtils.get(user.getPhone());
        if (StringUtils.isBlank(cacheCode)) {
            return ResponseResult.error("验证码不存在或已过期，请重新获取！");
        }
        if (!StringUtils.equals(cacheCode, passwordVO.getValidateCode())) {
            return ResponseResult.error("验证码不正确！");
        }

        // 两次密码比较
        if (!StringUtils.equals(passwordVO.getNewPassword(), passwordVO.getConfirmNewPassword())) {
            return ResponseResult.error("两次密码填写不一致，请重新填写！");
        }
        if (userService.changeUserPwd(passwordVO, user)) {
            return ResponseResult.ok(user);
        }
        return ResponseResult.error();
    }

    /**
     * 修改当前用户密码
     *
     * @param passwordVO 密码参数
     */
    @PutMapping("/changeCurrentUserPwd")
    @RequiresPermissions(PrivCode.BTN_CODE_USER_SAVE)
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "修改当前用户密码")
    public ResponseResult<?> changeCurrentUserPwd(@Valid @RequestBody PasswordVO passwordVO) {
        User user = SubjectUtils.getUser();
        assert user != null;
        // 比较原密码是否填写正确
        if (!user.getPassword().equals(SecureUtils.simpleMD5(user.getLoginname(), passwordVO.getPassword()))) {
            return ResponseResult.error("当前密码填写错误，请重新填写！");
        }

        // 两次密码比较
        if (!StringUtils.equals(passwordVO.getNewPassword(), passwordVO.getConfirmNewPassword())) {
            return ResponseResult.error("两次密码填写不一致，请重新填写！");
        }
        if (userService.changeCurrentUserPwd(passwordVO, user)) {
            return ResponseResult.ok(user);
        }
        return ResponseResult.error();
    }

    @PostMapping("/uploadUserImg")
    @RequiresPermissions(PrivCode.BTN_CODE_USER_SAVE)
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "更换用户头像")
    public ResponseResult<?> uploadUserImg(MultipartFile file) {
        ResponseResult<Map<String, String>> upload = upload(file);
        // 将文件路径保存到数据库
        if (upload.isOk()) {
            User user = userService.getById(SubjectUtils.getUserId());
            // 删除旧的头像img文件
            FileUtils.del(FileUtils.getRealFilePath(user.getPhotoPath()));
            user.setPhotoPath(upload.getData().get("filePath"));
            userService.updateById(user);
            SubjectUtils.setUser(user);
        }
        return upload;
    }

    @DeleteMapping("/delUser")
    @RequiresPermissions(PrivCode.BTN_CODE_USER_DELETE)
    @ResponseBody
    @RecordLog(type = OperateType.DELETE, description = "删除用户")
    public ResponseResult<?> delUser(String userIds) {
        if (userService.removeByIds(Arrays.asList(userIds.split(",")))) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
    }

    /**
     * 获取手机验证码
     */
    @GetMapping("/noNeedLogin/getValidateCode")
    @ResponseBody
    public ResponseResult<?> getValidateCode(String phone) {
        Assert.notBlank(phone);
        String validateCode = String.valueOf(RandomUtil.randomInt(100000, 1000000));
        // 缓存时间，分钟
        long timeout = 5;
        CacheUtils.put(phone, validateCode, DateUnit.MINUTE.getMillis() * timeout);
        // 从文件中获取短信配置相关参数
        SmsConfigVO smsConfigVO = FileUtils.readObjectFromFile(SmsUtils.DAT_SMS_CONFIG, SmsConfigVO.class);
        // 设置短信参数
        SmsConfig config = new SmsConfig();
        config.setSmsProvider(smsConfigVO.getSmsProvider());
        config.setAppId(smsConfigVO.getAppId());
        config.setSecretId(smsConfigVO.getSecretId());
        config.setSecretKey(SecureUtils.aesDecrypt(smsConfigVO.getSecretKey()));
        config.setSign(smsConfigVO.getSign());
        config.setPhones(CollectionUtil.newArrayList("+86" + phone));
        config.setTemplateID(smsConfigVO.getTemplateParams().get(TemplateType.VALIDATE_CODE.type()).getTemplateID());
        Map<String, String> templateParams = new HashMap<>();
        templateParams.put("1", validateCode);
        templateParams.put("2", String.valueOf(timeout));
        config.setTemplateParams(templateParams);
        // 发送
        String code = NotifyServiceFactory.build(config).send();
        Map<String, String> retData = new HashMap<>(4);
        retData.put("code", code);
        retData.put("phone", phone);
        if (StringUtils.equalsIgnoreCase(code, Constant.OK)) {
            return ResponseResult.ok(retData);
        }
        return ResponseResult.error(retData);
    }

}

