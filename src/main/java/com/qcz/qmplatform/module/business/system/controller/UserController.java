package com.qcz.qmplatform.module.business.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.ImportResult;
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
import com.qcz.qmplatform.common.validation.groups.Update;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.business.notify.NotifyServiceFactory;
import com.qcz.qmplatform.module.business.notify.domain.pojo.SmsConfig;
import com.qcz.qmplatform.module.business.notify.domain.pojo.TemplateParam;
import com.qcz.qmplatform.module.business.notify.domain.pojo.TemplateType;
import com.qcz.qmplatform.module.business.notify.domain.vo.SmsConfigVO;
import com.qcz.qmplatform.module.business.system.domain.User;
import com.qcz.qmplatform.module.business.system.domain.dto.SaveUserDTO;
import com.qcz.qmplatform.module.business.system.domain.qo.UserGroupUserQO;
import com.qcz.qmplatform.module.business.system.domain.qo.UserQO;
import com.qcz.qmplatform.module.business.system.domain.dto.CurrentUserInfoDTO;
import com.qcz.qmplatform.module.business.system.domain.dto.PasswordDTO;
import com.qcz.qmplatform.module.business.system.domain.vo.UserGroupUserVO;
import com.qcz.qmplatform.module.business.system.domain.vo.UserVO;
import com.qcz.qmplatform.module.business.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
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

    @Resource
    private UserService userService;

    @GetMapping("/userListPage")
    public String userListPage() {
        return "/module/system/userList";
    }

    @GetMapping("/userDetail")
    public String userDetail() {
        return "/module/system/userDetail";
    }

    @GetMapping("/safeSettingPage")
    public String safeSettingPage() {
        return "/module/system/safeSetting";
    }

    @GetMapping("/uploadUserImgPage")
    public String uploadUserImgPage() {
        return "/module/system/uploadUserImg";
    }

    /**
     * 个人基本资料
     */
    @GetMapping("/personalBasicInfoPage")
    public String personalBasicInfoPage() {
        return "/module/system/personalBasicInfo";
    }

    /**
     * 找回密码
     */
    @GetMapping("/nnl/retrievePasswordPage")
    public String retrievePasswordPage() {
        return "/module/system/retrievePassword";
    }

    /**
     * 选择用户树
     */
    @GetMapping("/chooseUserTree")
    public String chooseUserTree() {
        return "/module/system/chooseUserTree";
    }

    @GetMapping("/getUser/{userId}")
    @ResponseBody
    public UserVO getUserInfo(@PathVariable String userId) {
        return userService.getUserOne(userId);
    }

    @PostMapping("/getUserList")
    @ResponseBody
    public ResponseResult<PageResult<UserVO>> getUserList(PageRequest pageRequest, UserQO user, boolean export) {
        String organizationIdsStr = user.getOrganizationIdsStr();
        if (StringUtils.isNotBlank(organizationIdsStr)) {
            user.setOrganizationIds(CollectionUtil.newArrayList(organizationIdsStr.split(",")));
        }
        return ResponseResult.ok(PageResultHelper.parseResult(userService.getUserList(user, pageRequest, export)));
    }

    /**
     * 获取用户组用户列表
     */
    @PostMapping("/getUserGroupUserList")
    @ResponseBody
    public ResponseResult<PageResult<UserGroupUserVO>> getUserGroupUserList(PageRequest pageRequest, UserGroupUserQO qo) {
        return ResponseResult.ok(PageResultHelper.parseResult(userService.getUserGroupUserList(pageRequest, qo)));
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

    @GetMapping("/nnl/queryUserByName")
    @ResponseBody
    public ResponseResult<UserVO> queryUserByName(String name) {
        return ResponseResult.ok(userService.queryUserByName(name));
    }

    @GetMapping("/nnl/queryPhoneByName")
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
    public ResponseResult<CurrentUserInfoDTO> getCurrentUserInfo() {
        return ResponseResult.ok(userService.getCurrentUserInfo());
    }

    @PostMapping("/addUser")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_USER_SAVE)
    @RecordLog(type = OperateType.INSERT, description = "新增用户")
    public ResponseResult<Void> addUser(@Validated @RequestBody SaveUserDTO user) {
        return ResponseResult.newInstance(userService.insertUser(user));
    }

    @PutMapping("/updateUser")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_USER_SAVE)
    @RecordLog(type = OperateType.UPDATE, description = "修改用户")
    public ResponseResult<Void> updateUser(@Validated @RequestBody SaveUserDTO user) {
        return ResponseResult.newInstance(userService.updateUser(user));
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
    public ResponseResult<Void> saveCurrentUserInfo(@Validated @RequestBody CurrentUserInfoDTO user) {
        return ResponseResult.newInstance(userService.saveCurrentUserInfo(user));
    }

    @PutMapping("/nnl/changeUserPwd")
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "找回密码")
    public ResponseResult<?> changeUserPwd(@Validated({Update.class}) @RequestBody PasswordDTO passwordDTO) {
        UserVO user = userService.queryUserByName(passwordDTO.getLoginname());

        String cacheCode = (String) CacheUtils.get(user.getPhone());
        if (StringUtils.isBlank(cacheCode)) {
            return ResponseResult.error("验证码不存在或已过期，请重新获取！");
        }
        if (!StringUtils.equals(cacheCode, passwordDTO.getValidateCode())) {
            return ResponseResult.error("验证码不正确！");
        }

        // 两次密码比较
        if (!StringUtils.equals(passwordDTO.getNewPassword(), passwordDTO.getConfirmNewPassword())) {
            return ResponseResult.error("两次密码填写不一致，请重新填写！");
        }
        if (userService.changeUserPwd(passwordDTO, user)) {
            return ResponseResult.ok(user);
        }
        return ResponseResult.error();
    }

    /**
     * 修改当前用户密码
     *
     * @param passwordDTO 密码参数
     */
    @PutMapping("/changeCurrentUserPwd")
    @RequiresPermissions(PrivCode.BTN_CODE_USER_SAVE)
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "修改当前用户密码")
    public ResponseResult<?> changeCurrentUserPwd(@Validated({Update.class}) @RequestBody PasswordDTO passwordDTO) {
        User user = SubjectUtils.getUser();
        assert user != null;
        // 比较原密码是否填写正确
        if (!SecureUtils.accountCheck(passwordDTO.getPassword(), user.getPassword())) {
            return ResponseResult.error("当前密码填写错误，请重新填写！");
        }

        // 两次密码比较
        if (!StringUtils.equals(passwordDTO.getNewPassword(), passwordDTO.getConfirmNewPassword())) {
            return ResponseResult.error("两次密码填写不一致，请重新填写！");
        }
        if (userService.changeCurrentUserPwd(passwordDTO, user)) {
            return ResponseResult.ok(user);
        }
        return ResponseResult.error();
    }

    @PostMapping("/uploadUserImg")
    @RequiresPermissions(PrivCode.BTN_CODE_USER_SAVE)
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "更换用户头像")
    public ResponseResult<Map<String, String>> uploadUserImg(MultipartFile file) {
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
    public ResponseResult<Void> delUser(String userIds) {
        userService.deleteUserByIds(StringUtils.split(userIds, ","));
        return ResponseResult.ok();
    }

    /**
     * 获取手机验证码
     */
    @GetMapping("/nnl/getValidateCode")
    @ResponseBody
    public ResponseResult<Map<String, String>> getValidateCode(String phone) {
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
        config.setAppKey(SecureUtils.aesDecrypt(smsConfigVO.getAppKey()));
        config.setSecretId(smsConfigVO.getSecretId());
        config.setSecretKey(SecureUtils.aesDecrypt(smsConfigVO.getSecretKey()));
        config.setSign(smsConfigVO.getSign());
        config.setPhones(CollectionUtil.newArrayList(phone));
        // 短信模块参数
        TemplateParam templateParam = smsConfigVO.getTemplateParams().get(TemplateType.VALIDATE_CODE.type());
        config.setTemplateID(templateParam.getTemplateID());
        config.setTemplateParamCnt(templateParam.getParamCnt());
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

    @PostMapping("/importExcel")
    @ResponseBody
    public ResponseResult<ImportResult> importExcel(MultipartFile file) {
        return userService.insertImportExcel(getExcelData(file, 0, UserVO.class));
    }

}

