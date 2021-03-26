package com.qcz.qmplatform.module.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private UserOrganizationMapper userOrganizationMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    public List<UserVO> getUserList(UserQO user) {
        return baseMapper.queryUserList(user);
    }

    public User findByLoginNameAndPassword(String loginName, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginname", loginName);
        queryWrapper.eq("password", password);
        return this.getOne(queryWrapper);
    }

    public UserVO queryUserByName(String username) {
        return baseMapper.queryUserByName(username);
    }

    public boolean insertUser(UserVO user) {
        String userId = IdUtil.randomUUID();
        user.setId(userId);
        user.setCreateTime(DateUtils.getCurrTimestamp());
        user.setCreateUserId(SubjectUtils.getUserId());
        if (StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(SubjectUtils.md5Encrypt(user.getLoginname(), user.getPassword()));
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
        if (SubjectUtils.passwordChanged(password)) {
            newPwd = SubjectUtils.md5Encrypt(user.getLoginname(), password);
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
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.ne(StringUtils.isNotBlank(userId), "id", userId);
        wrapper.eq("loginname", loginname);
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
        user.setPassword(SubjectUtils.md5Encrypt(user.getLoginname(), passwordVO.getNewPassword()));
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
        user.setPassword(SubjectUtils.md5Encrypt(user.getLoginname(), passwordVO.getNewPassword()));
        return updateById(user);
    }
}
