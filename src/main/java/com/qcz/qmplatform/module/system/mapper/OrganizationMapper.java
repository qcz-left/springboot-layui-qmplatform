package com.qcz.qmplatform.module.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcz.qmplatform.module.system.domain.Organization;
import com.qcz.qmplatform.module.system.domain.pojo.OrgTree;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
public interface OrganizationMapper extends BaseMapper<Organization> {

    List<OrgTree> selectOrgTree(Map<String, Object> queryParams);

    List<OrgTree> selectOrgUserTree(Organization organization);

}
