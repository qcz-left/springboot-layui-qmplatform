package com.qcz.qmplatform.module.system.controller;

import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.PageRequest;
import com.qcz.qmplatform.common.bean.PageResult;
import com.qcz.qmplatform.common.bean.PageResultHelper;
import com.qcz.qmplatform.common.bean.PrivCode;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.utils.FileUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.system.domain.User;
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
import java.io.IOException;
import java.util.Arrays;
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

    @GetMapping("/getUser/{userId}")
    @ResponseBody
    public UserVO getUserInfo(@PathVariable String userId) {
        return userService.getUserOne(userId);
    }

    @GetMapping("/getUserList")
    @ResponseBody
    public ResponseResult<PageResult> getUserList(PageRequest pageRequest, User user) {
        PageResultHelper.startPage(pageRequest);
        return ResponseResult.ok(PageResultHelper.parseResult(userService.getUserList(user)));
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

    @GetMapping("/queryUserByUsername")
    @ResponseBody
    public ResponseResult<UserVO> queryUserByUsername(String username) {
        return ResponseResult.ok(userService.queryUserByUsername(username));
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
        User user = userService.getById(SubjectUtils.getUserId());
        // 比较原密码是否填写正确
        if (!user.getPassword().equals(SubjectUtils.md5Encrypt(user.getLoginname(), passwordVO.getPassword()))) {
            return ResponseResult.error("当前密码填写错误，请重新填写！");
        }
        // 两次密码比较
        if (!StringUtils.equals(passwordVO.getNewPassword(), passwordVO.getConfirmNewPassword())) {
            return ResponseResult.error("两次密码填写不一致，请重新填写！");
        }
        if (userService.changeCurrentUserPwd(passwordVO, user)) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
    }

    @PostMapping("/uploadUserImg")
    @RequiresPermissions(PrivCode.BTN_CODE_USER_SAVE)
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "更换用户头像")
    public ResponseResult<?> uploadUserImg(MultipartFile file) throws IOException {
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

}

