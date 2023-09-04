package com.qcz.qmplatform.module.business.system.realm;

import cn.hutool.core.collection.CollectionUtil;
import com.qcz.qmplatform.common.utils.SecureUtils;
import com.qcz.qmplatform.common.utils.SpringContextUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.module.business.system.domain.assist.LoginType;
import com.qcz.qmplatform.module.business.system.mapper.UserMapper;
import com.qcz.qmplatform.module.business.system.mapper.UserRoleMapper;
import com.qcz.qmplatform.module.business.system.service.UserService;
import com.qcz.qmplatform.module.business.system.domain.vo.UserVO;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

/**
 * shiro realm 身份权限配置
 *
 * @author quchangzhong
 */
public class UserRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserMapper userMapper = SpringContextUtils.getBean(UserMapper.class);
        UserRoleMapper userRoleMapper = SpringContextUtils.getBean(UserRoleMapper.class);

        String userId = SubjectUtils.getUserId();
        Set<String> authorities = new HashSet<>(userMapper.queryAuthoritiesByUserId(userId));
        Set<String> roles = new HashSet<>(CollectionUtil.getFieldValues(userRoleMapper.getRoleByUserId(userId), "roleSign", String.class));

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(authorities);
        info.addRoles(roles);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        CustomToken upToken = (CustomToken) token;

        String loginName = upToken.getUsername();
        String password = new String((char[]) upToken.getCredentials());

        UserService userService = SpringContextUtils.getBean(UserService.class);
        UserVO user = userService.queryUserByName(loginName);
        // 账号不存在
        if (user == null) {
            throw new UnknownAccountException("不存在该账号");
        }

        // 账号被锁定
        if (user.getLocked() == 1) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        // 密码错误
        if (upToken.getType() == LoginType.PASSWORD && !SecureUtils.accountCheck(password, user.getPassword())) {
            throw new IncorrectCredentialsException("密码错误");
        }

        // 盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(loginName);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getId(), user.getPassword(), credentialsSalt, getName());
        SubjectUtils.setUser(user);
        return info;
    }

}
