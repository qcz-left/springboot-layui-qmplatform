package com.qcz.qmplatform.module.sys.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.base.BaseService;
import com.qcz.qmplatform.common.message.ResponseResult;
import com.qcz.qmplatform.common.utils.Constants;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.common.utils.TimeUtils;
import com.qcz.qmplatform.module.sys.dao.UserDao;
import com.qcz.qmplatform.module.sys.entity.User;
import com.qcz.qmplatform.module.sys.form.PasswordForm;

import tk.mybatis.mapper.entity.Example;

/**
 * 用户 逻辑层
 * @author changzhongq
 * @time 2018年11月13日 下午2:59:48
 */
@Service
@Module("用户管理")
public class UserService extends BaseService<User, UserDao> {

	@Autowired
	private RoleService roleService;

	public User findByLoginNameAndPassword(String loginName, String password) {
		return mapper.selectOne(new User(loginName, password));
	}

	public List<User> findUserList(Map<String, Object> paramMap) {

		return mapper.findUserList(paramMap);
	}

	/**
	 * 根据登录名查找用户
	 * @param loginName 登录名
	 * @return
	 */
	public ResponseResult findByLoginName(String loginName) {
		User user = mapper.selectOne(new User(loginName));
		if (user == null) {
			return ResponseResult.ok();
		}
		return ResponseResult.error("该登录名已存在，请更换！");
	}

	@Transactional
	public ResponseResult saveUser(User data, String roleIds) {
		Object result = null;
		String userId = data.getUserId();
		User user = SubjectUtils.getUser();
		Timestamp timestamp = TimeUtils.getTimestamp();
		data.setUpdateTime(timestamp);
		data.setUpdateUserId(user.getUserId());
		data.setUpdateUserName(user.getUserName());
		// 设置默认密码12345678
		data.setLoginPassword(SubjectUtils.md5Encrypt(data.getLoginName(), Constants.DEFAULT_USER_LOGIN_PASSWORD));
		if (StringUtils.isBlank(userId)) {
			userId = StringUtils.getUUID();
			data.setUserId(userId);
			data.setCreateUserId(user.getUserId());
			data.setCreateUserName(user.getUserName());
			data.setCreateTime(timestamp);
		}
		result = ((UserService) AopContext.currentProxy()).save(data);
		if (!StringUtils.isBlank(roleIds)) {
			roleService.bindRole(userId, roleIds.split(","));
		}
		return ResponseResult.ok("保存成功", result);
	}

	/**
	 * 更新用户状态
	 * @param data
	 * @return
	 */
	public ResponseResult changeLockedStatus(User newData) {
		User data = mapper.selectByPrimaryKey(newData.getUserId());
		if (data == null) {
			return ResponseResult.error("用户不存在！");
		}
		data.setLocked(newData.getLocked());
		return mapper.updateByPrimaryKey(data) > 0 ? ResponseResult.ok("操作成功！", data) : ResponseResult.error("操作失败！");
	}

	/**
	 * 安全设置
	 * @param data
	 * @return
	 */
	public ResponseResult safeSetting(PasswordForm form) {
		String oldLoginPassword = form.oldLoginPassword;// 原密码
		User user = SubjectUtils.getUser();
		if (StringUtils.isBlank(oldLoginPassword)) {
			return ResponseResult.error("原密码不能为空！");
		}
		String loginName = user.getLoginName();
		if (!oldLoginPassword.equals(oldLoginPassword)) {
			return ResponseResult.error("原密码不正确，请重新输入！");
		}
		String loginPassword = form.loginPassword;// 新密码
		if (StringUtils.isBlank(loginPassword)) {
			return ResponseResult.error("登录密码不能为空！");
		}
		int result = mapper.updatePasswordById(user.getUserId(), SubjectUtils.md5Encrypt(loginName, loginPassword));
		return result > 0 ? ResponseResult.ok("修改密码成功！", null) : ResponseResult.error("修改密码失败！");
	}

	/**
	 * 重置用户密码
	 * @param userId 用户id
	 * @return
	 */
	public ResponseResult resetLoginPassword(String userId) {
		if (StringUtils.isBlank(userId)) {
			throw new NullPointerException("userId 不能为空！");
		}
		Example example = new Example(User.class);
		example.createCriteria().andEqualTo("userId", userId);
		User user = this.find(userId);
		int result = mapper.updatePasswordById(userId, SubjectUtils.md5Encrypt(user.getLoginName(), Constants.DEFAULT_USER_LOGIN_PASSWORD));
		return result > 0 ? ResponseResult.ok("重置成功！", null) : ResponseResult.error("重置失败！");
	}
}
