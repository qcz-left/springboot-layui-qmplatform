package com.qcz.qmplatform.module.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcz.qmplatform.module.system.domain.Organization;
import com.qcz.qmplatform.module.system.pojo.OrgTree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
public interface OrganizationMapper extends BaseMapper<Organization> {

    List<OrgTree> selectOrgTree(Organization organization);

    List<String> selectCascOrgIds(@Param("organizationIds") List<String> organizationIds);

    void deleteOrgById(@Param("organizationIds") List<String> organizationIds);
}
