package com.qcz.qmplatform.module.system.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.IdUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.TreeUtils;
import com.qcz.qmplatform.module.system.domain.Organization;
import com.qcz.qmplatform.module.system.domain.UserOrganization;
import com.qcz.qmplatform.module.system.mapper.OrganizationMapper;
import com.qcz.qmplatform.module.system.domain.pojo.OrgTree;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Resource
    private UserOrganizationService userOrganizationService;

    public List<OrgTree> getOrgList(Organization organization) {
        return baseMapper.selectOrgTree(organization);
    }

    public List<OrgTree> getOrgUserTree(Organization organization) {
        return TreeUtils.buildTree(baseMapper.selectOrgUserTree(organization));
    }

    public List<OrgTree> getOrgTree(Organization organization) {
        return TreeUtils.buildTree(getOrgList(organization));
    }

    public boolean addOrgOne(Organization org) {
        org.setOrganizationId(IdUtils.getUUID());
        org.setCreateTime(DateUtils.getCurrTimestamp());
        return save(org);
    }

    public boolean updateOrgOne(Organization org) {
        return updateById(org);
    }

    public boolean deleteOrg(List<String> orgIds) {
        List<String> cascOrgIds = this.queryOrgIdRecursive(orgIds);
        removeByIds(cascOrgIds);
        // 删除关联部门信息
        LambdaQueryWrapper<UserOrganization> deleteWrapper = Wrappers.lambdaQuery(UserOrganization.class)
                .in(UserOrganization::getOrganizationId, cascOrgIds);
        userOrganizationService.remove(deleteWrapper);
        return true;
    }

    /**
     * 向下递归查询出所有部门id
     */
    public List<String> queryOrgIdRecursive(List<String> orgIds) {
        List<String> allIds = new ArrayList<>();

        if (CollectionUtil.isNotEmpty(orgIds)) {
            CollectionUtil.addAll(allIds, orgIds);

            List<String> childIds = new ArrayList<>();
            CollectionUtil.addAll(childIds, baseMapper.selectObjs(
                    Wrappers.lambdaQuery(Organization.class)
                            .in(Organization::getParentId, orgIds)
                            .select(Organization::getOrganizationId)
            ));

            CollectionUtil.addAll(allIds, queryOrgIdRecursive(childIds));
        }

        return allIds;
    }

    public boolean saveOrgOne(Organization org) {
        if (StringUtils.isBlank(org.getOrganizationId())) {
            return addOrgOne(org);
        }
        return updateOrgOne(org);
    }

    public Organization getByName(String orgName) {
        LambdaQueryWrapper<Organization> queryWrapper = Wrappers.lambdaQuery(Organization.class)
                .in(Organization::getOrganizationName, orgName);
        return getOne(queryWrapper);
    }
}
