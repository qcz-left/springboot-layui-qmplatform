package com.qcz.qmplatform.module.business.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.bean.ExcelRow;
import com.qcz.qmplatform.common.bean.ImportFailReason;
import com.qcz.qmplatform.common.bean.ImportResult;
import com.qcz.qmplatform.common.bean.PageRequest;
import com.qcz.qmplatform.common.bean.PageResultHelper;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.constant.Constant;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.IdUtils;
import com.qcz.qmplatform.common.utils.SecureUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.module.business.system.domain.Organization;
import com.qcz.qmplatform.module.business.system.domain.Role;
import com.qcz.qmplatform.module.business.system.domain.User;
import com.qcz.qmplatform.module.business.system.domain.UserOrganization;
import com.qcz.qmplatform.module.business.system.domain.UserRole;
import com.qcz.qmplatform.module.business.system.domain.dto.SaveUserDTO;
import com.qcz.qmplatform.module.business.system.domain.qo.UserGroupUserQO;
import com.qcz.qmplatform.module.business.system.domain.qo.UserQO;
import com.qcz.qmplatform.module.business.system.domain.vo.CurrentUserInfoVO;
import com.qcz.qmplatform.module.business.system.domain.vo.PasswordVO;
import com.qcz.qmplatform.module.business.system.domain.vo.UserGroupUserVO;
import com.qcz.qmplatform.module.business.system.domain.vo.UserVO;
import com.qcz.qmplatform.module.business.system.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    /**
     * Excel 属性映射
     */
    private static class ExcelPropertyMap {

        public static final Map<String, String> USER_SEX = MapUtil.newHashMap();
        public static final Map<String, Integer> LOCKED = MapUtil.newHashMap();

        static {
            USER_SEX.put("男", "1");
            USER_SEX.put("女", "2");
            LOCKED.put("正常", 0);
            LOCKED.put("锁定", 1);
        }

    }

    @Resource
    private UserOrganizationService userOrganizationService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private UserUserGroupService userUserGroupService;

    @Resource
    private OrganizationService organizationService;

    /**
     * 查询数据列表
     *
     * @param user        查询参数
     * @param pageRequest 分页参数
     * @param export      是否导出
     */
    public List<UserVO> getUserList(UserQO user, PageRequest pageRequest, boolean export) {
        if (Objects.nonNull(user)) {
            user.setCascOrganizationIds(organizationService.queryOrgIdRecursive(user.getOrganizationIds()));
        }
        if (!export) {
            PageResultHelper.startPage(pageRequest);
        }
        return baseMapper.queryUserList(user);
    }

    public List<UserVO> queryAllAdmin() {
        return baseMapper.queryByRoleSign(Constant.SYSTEM_ADMIN);
    }

    public UserVO queryUserByName(String loginName) {
        return baseMapper.queryUserByName(loginName);
    }

    public boolean insertUser(SaveUserDTO user) {
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

    public boolean updateUser(SaveUserDTO user) {
        String userId = user.getId();
        // 先删除关联的组织机构和角色，在增加
        userOrganizationService.deleteByUserIds(CollectionUtil.newArrayList(userId));
        userRoleService.deleteByUserIds(CollectionUtil.newArrayList(userId));
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
        userVo.setOrganizationIds(userOrganizationService.getBaseMapper().getOrganizationIdsByUserId(userId));
        userVo.setRoleIds(CollectionUtil.getFieldValues(userRoleService.getBaseMapper().getRoleByUserId(userId), "roleId", String.class));
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
            userOrganizationService.save(userOrganization);
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
            userRoleService.save(userRole);
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
        List<Role> roles = userRoleService.getBaseMapper().getRoleByUserId(user.getId());
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
            userVO.setUserSex(ExcelPropertyMap.USER_SEX.get(userSexName));
            userVO.setLocked(ExcelPropertyMap.LOCKED.get(lockedName));
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
                insertUser(BeanUtil.copyProperties(userVO, SaveUserDTO.class));
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

    public void deleteUserByIds(List<String> userIds) {
        removeByIds(userIds);
        userOrganizationService.deleteByUserIds(userIds);
        userRoleService.deleteByUserIds(userIds);
        userUserGroupService.deleteByUserIds(userIds);
    }


    public List<UserGroupUserVO> getUserGroupUserList(PageRequest pageRequest, UserGroupUserQO qo) {
        PageResultHelper.startPage(pageRequest);
        return baseMapper.getUserGroupUserList(qo);
    }
}
