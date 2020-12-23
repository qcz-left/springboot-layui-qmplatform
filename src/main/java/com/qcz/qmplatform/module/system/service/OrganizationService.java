package com.qcz.qmplatform.module.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.TreeUtils;
import com.qcz.qmplatform.module.system.domain.Organization;
import com.qcz.qmplatform.module.system.mapper.OrganizationMapper;
import com.qcz.qmplatform.module.system.po.OrgTree;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 组织机构服务实现类
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
@Service
public class OrganizationService extends ServiceImpl<OrganizationMapper, Organization> {

    public List<OrgTree> getOrgList(Organization organization) {
        return baseMapper.selectOrgTree(organization);
    }

    public List<OrgTree> getOrgTree(Organization organization) {
        return TreeUtils.buildTree(getOrgList(organization));
    }

    public boolean addOrgOne(Organization org) {
        org.setOrganizationId(StringUtils.uuid());
        org.setCreateTime(DateUtils.getCurrTimestamp());
        return save(org);
    }

    public boolean updateOrgOne(Organization org) {
        return updateById(org);
    }

    public boolean deleteOrg(List<String> orgIds) {
        return removeByIds(orgIds);
    }

    public boolean saveOrgOne(Organization org) {
        if (StringUtils.isBlank(org.getOrganizationId())) {
            return addOrgOne(org);
        }
        return updateOrgOne(org);
    }
}
