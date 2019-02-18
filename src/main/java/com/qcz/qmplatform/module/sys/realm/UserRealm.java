package com.qcz.qmplatform.module.sys.realm;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import com.qcz.qmplatform.common.utils.SpringContextUtil;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.module.sys.entity.User;
import com.qcz.qmplatform.module.sys.service.MenuService;
import com.qcz.qmplatform.module.sys.service.UserService;

/**
 * shiro realm 身份权限配置
 * @author quchangzhong
 * @time 2018年2月20日 下午6:56:13
 */
@Component
public class UserRealm extends AuthorizingRealm {
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		MenuService menuService = (MenuService) SpringContextUtil.getBean(MenuService.class);
		Set<String> set = menuService.listRoles(SubjectUtils.getUserId());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(set);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		String password = SubjectUtils.md5Encrypt(username, new String((char[]) upToken.getCredentials()));
		UserService userService = (UserService) SpringContextUtil.getBean(UserService.class);
		User user = userService.findByLoginNameAndPassword(username, password);
		// 账号不存在
		if (user == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}

		// 密码错误
		if (!password.equals(user.getLoginPassword())) {
			throw new IncorrectCredentialsException("账号或密码不正确");
		}
		
		// 账号被锁定
		if ("0".equals(user.getLocked())) {
			throw new LockedAccountException("账号已被锁定,请联系管理员");
		}
		// 盐值
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, credentialsSalt, getName());
		SubjectUtils.setUser(user);
		return info;
	}
	
}
