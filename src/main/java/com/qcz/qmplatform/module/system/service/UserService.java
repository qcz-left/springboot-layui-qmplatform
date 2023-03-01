package com.qcz.qmplatform.module.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.bean.ExcelRow;
import com.qcz.qmplatform.common.bean.ImportFailReason;
import com.qcz.qmplatform.common.bean.ImportResult;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.constant.Constant;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.IdUtils;
import com.qcz.qmplatform.common.utils.SecureUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.module.system.domain.Organization;
import com.qcz.qmplatform.module.system.domain.Role;
import com.qcz.qmplatform.module.system.domain.User;
import com.qcz.qmplatform.module.system.domain.UserOrganization;
import com.qcz.qmplatform.module.system.domain.UserRole;
import com.qcz.qmplatform.module.system.mapper.UserMapper;
import com.qcz.qmplatform.module.system.mapper.UserOrganizationMapper;
import com.qcz.qmplatform.module.system.mapper.UserRoleMapper;
import com.qcz.qmplatform.module.system.qo.UserQO;
import com.qcz.qmplatform.module.system.vo.CurrentUserInfoVO;
import com.qcz.qmplatform.module.system.vo.PasswordVO;
import com.qcz.qmplatform.module.system.vo.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户服务实现类
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Resource
    private UserOrganizationMapper userOrganizationMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private OrganizationService organizationService;

    public List<UserVO> getUserList(UserQO user) {
        return baseMapper.queryUserList(user);
    }

    public List<UserVO> queryAllAdmin() {
        return baseMapper.queryByRoleSign(Constant.SYSTEM_ADMIN);
    }

    public User findByLoginNameAndPassword(String loginName, String password) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery(User.class)
                .eq(User::getLoginname, loginName)
                .eq(User::getPassword, password);
        return this.getOne(queryWrapper);
    }

    public UserVO queryUserByName(String loginName) {
        return baseMapper.queryUserByName(loginName);
    }

    public boolean insertUser(UserVO user) {
        String userId = IdUtils.getUUID();
        user.setId(userId);
        user.setCreateTime(DateUtils.getCurrTimestamp());
        user.setCreateUserId(SubjectUtils.getUserId());
        if (StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(SecureUtils.accountEncrypt(user.getPassword()));
        }
        insertUserRole(userId, user.getRoleIds());
        insertUserOrg(userId, user.getOrganizationIds());
        return save(user);
    }

    public boolean updateUser(UserVO user) {
        String userId = user.getId();
        // 先删除关联的组织机构和角色，在增加
        Map<String, Object> params = new HashMap<>(2);
        params.put("user_id", userId);
        userOrganizationMapper.deleteByMap(params);
        userRoleMapper.deleteByMap(params);
        insertUserOrg(userId, user.getOrganizationIds());
        insertUserRole(userId, user.getRoleIds());
        // 处理密码
        String newPwd;
        String password = user.getPassword();
        if (SecureUtils.passwordChanged(password)) {
            newPwd = SecureUtils.accountEncrypt(password);
        } else {
            newPwd = getById(user.getId()).getPassword();
        }
        user.setPassword(newPwd);
        return updateById(user);
    }

    /**
     * 校验登录名唯一
     *
     * @param loginname the login name
     * @param userId    the user id
     * @return true: not exists , or false: exists
     */
    public boolean validateLoginName(String loginname, String userId) {
        Assert.notBlank(loginname);
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class)
                .ne(StringUtils.isNotBlank(userId), User::getId, userId)
                .eq(User::getLoginname, loginname);
        return baseMapper.selectCount(wrapper) == 0;
    }

    public UserVO getUserOne(String userId) {
        User user = getById(userId);
        UserVO userVo = new UserVO();
        BeanUtil.copyProperties(user, userVo);
        userVo.setOrganizationIds(userOrganizationMapper.getOrganizationIdsByUserId(userId));
        userVo.setRoleIds(CollectionUtil.getFieldValues(userRoleMapper.getRoleByUserId(userId), "roleId", String.class));
        return userVo;
    }

    private void insertUserOrg(String userId, List<String> organizationIds) {
        if (organizationIds == null) {
            return;
        }
        for (String organizationId : organizationIds) {
            UserOrganization userOrganization = new UserOrganization();
            userOrganization.setUserId(userId);
            userOrganization.setOrganizationId(organizationId);
            userOrganizationMapper.insert(userOrganization);
        }
    }

    private void insertUserRole(String userId, List<String> roleIds) {
        if (roleIds == null) {
            return;
        }
        for (String roleId : roleIds) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        }
    }

    /**
     * 获取当前用户信息
     */
    public CurrentUserInfoVO getCurrentUserInfo() {
        CurrentUserInfoVO currentUserInfoVO = new CurrentUserInfoVO();
        User user = SubjectUtils.getUser();
        assert user != null;
        currentUserInfoVO.setLoginname(user.getLoginname());
        currentUserInfoVO.setUsername(user.getUsername());
        currentUserInfoVO.setEmailAddr(user.getEmailAddr());
        currentUserInfoVO.setPhone(user.getPhone());
        currentUserInfoVO.setRemark(user.getRemark());
        currentUserInfoVO.setUserSex(user.getUserSex());
        List<Role> roles = userRoleMapper.getRoleByUserId(user.getId());
        currentUserInfoVO.setRoleName(CollectionUtil.join(CollectionUtil.getFieldValues(roles, "roleName", String.class), ","));
        return currentUserInfoVO;
    }

    /**
     * 修改当前登录用户个人基本资料
     *
     * @param currentUser 页面填写的用户信息
     */
    public boolean saveCurrentUserInfo(CurrentUserInfoVO currentUser) {
        User user = this.getById(SubjectUtils.getUserId());
        user.setUsername(currentUser.getUsername());
        user.setUserSex(currentUser.getUserSex());
        user.setEmailAddr(currentUser.getEmailAddr());
        user.setPhone(currentUser.getPhone());
        user.setRemark(currentUser.getRemark());
        SubjectUtils.setUser(user);
        return updateById(user);
    }

    /**
     * 修改当前用户密码
     *
     * @param passwordVO 密码信息
     * @param user       当前用户对象
     */
    public boolean changeCurrentUserPwd(PasswordVO passwordVO, User user) {
        user.setPassword(SecureUtils.accountEncrypt(passwordVO.getNewPassword()));
        SubjectUtils.setUser(user);
        return updateById(user);
    }

    /**
     * 修改用户密码
     *
     * @param passwordVO 密码信息
     * @param user       当前用户对象
     */
    public boolean changeUserPwd(PasswordVO passwordVO, User user) {
        user.setPassword(SecureUtils.accountEncrypt(passwordVO.getNewPassword()));
        return updateById(user);
    }

    /**
     * 锁定账号
     */
    public void lockAccount(String loginName) {
        LambdaUpdateWrapper<User> updateWrapper = Wrappers.lambdaUpdate(User.class)
                .set(User::getLocked, 1)
                .eq(User::getLoginname, loginName);
        this.update(updateWrapper);
    }

    /**
     * 导入Excel数据
     */
    public ResponseResult<ImportResult> insertImportExcel(List<ExcelRow<UserVO>> excelRowList) {
        ImportResult importResult = new ImportResult();
        List<ImportFailReason> importFailReasonList = new ArrayList<>();

        int successCount = 0;
        for (ExcelRow<UserVO> excelRow : excelRowList) {
            UserVO userVO = excelRow.getRow();
            long rowIndex = excelRow.getRowIndex();

            ImportFailReason importFailReason = new ImportFailReason();
            importFailReason.setRowIndex(rowIndex);
            importFailReason.setName(userVO.getLoginname());
            boolean canSuccess = true;

            String username = userVO.getUsername();
            if (StringUtils.isBlank(username)) {
                importFailReason.setReason("用户名不能为空");
                importFailReasonList.add(importFailReason);
                continue;
            }
            String loginname = userVO.getLoginname();
            if (StringUtils.isBlank(loginname)) {
                importFailReason.setReason("登录名不能为空");
                importFailReasonList.add(importFailReason);
                continue;
            }
            String phone = userVO.getPhone();
            if (StringUtils.isBlank(phone)) {
                importFailReason.setReason("手机号码不能为空");
                importFailReasonList.add(importFailReason);
                continue;
            }
            String emailAddr = userVO.getEmailAddr();
            if (StringUtils.isBlank(emailAddr)) {
                importFailReason.setReason("邮箱不能为空");
                importFailReasonList.add(importFailReason);
                continue;
            }
            if (queryUserByName(loginname) != null) {
                importFailReason.setReason("登录名已存在");
                importFailReasonList.add(importFailReason);
                continue;
            }
            String userSexName = userVO.getUserSexName();
            String lockedName = userVO.getLockedName();
            String organizationNames = userVO.getOrganizationName();
            String userId = IdUtils.getUUID();
            userVO.setId(userId);
            userVO.setPassword(SecureUtils.accountEncrypt(SecureUtils.DEFAULT_PASSWORD));
            userVO.setUserSex("男".equals(userSexName) ? "1" : ("女".equals(userSexName) ? "2" : ""));
            userVO.setLocked("正常".equals(lockedName) ? 1 : 0);
            if (StringUtils.isNotBlank(organizationNames)) {
                String[] orgNameArr = organizationNames.split(",");
                List<String> orgIds = new ArrayList<>();
                for (String orgName : orgNameArr) {
                    Organization org = organizationService.getByName(orgName);
                    if (org == null) {
                        canSuccess = false;
                        importFailReason.setReason("部门[" + orgName + "]不存在");
                        importFailReasonList.add(importFailReason);
                        continue;
                    }
                    orgIds.add(org.getOrganizationId());
                }
                userVO.setOrganizationIds(orgIds);
            }

            if (canSuccess) {
                insertUser(userVO);
                successCount++;
            }
        }

        int total = excelRowList.size();
        importResult.setImportFailReasonList(importFailReasonList);
        importResult.setTitle("登录名");
        importResult.setTotal(total);
        importResult.setSuccessCount(successCount);
        importResult.setFailCount(total - successCount);
        return ResponseResult.ok(importResult);
    }
}
