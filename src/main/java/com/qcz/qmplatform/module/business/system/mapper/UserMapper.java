package com.qcz.qmplatform.module.business.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcz.qmplatform.module.business.system.domain.User;
import com.qcz.qmplatform.module.business.system.domain.qo.UserQO;
import com.qcz.qmplatform.module.business.system.domain.vo.UserVO;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
public interface UserMapper extends BaseMapper<User> {
    UserVO queryUserByName(String loginName);

    List<String> queryAuthoritiesByUserId(String userId);

    List<UserVO> queryUserList(UserQO user);

    List<UserVO> queryByRoleSign(String roleSign);

}
