package com.qcz.qmplatform.module.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qcz.qmplatform.common.base.BaseDao;
import com.qcz.qmplatform.module.sys.entity.User;

/**
 * 用户数据
 * @author changzhongq
 * @time 2018年10月20日 下午10:19:56
 */
public interface UserDao extends BaseDao<User> {

	User findByLoginNameAndPassword(String username, String password);

	List<User> findUserList(Map<String, Object> paramMap);

	/**
	 * 根据用户id更新密码
	 * @param userId 用户id
	 * @param passwordWithMd5 md5加密后的密码
	 * @return
	 */
	int updatePasswordById(@Param("userId") String userId,@Param("passwordWithMd5")  String passwordWithMd5);

}
