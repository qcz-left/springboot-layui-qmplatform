package com.qcz.qmplatform.module.business.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcz.qmplatform.module.business.system.domain.UserOrganization;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
public interface UserOrganizationMapper extends BaseMapper<UserOrganization> {

    List<String> getOrganizationIdsByUserId(String userId);
}
