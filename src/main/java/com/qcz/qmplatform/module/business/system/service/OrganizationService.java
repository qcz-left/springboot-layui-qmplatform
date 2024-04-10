package com.qcz.qmplatform.module.business.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.constant.Constant;
import com.qcz.qmplatform.common.utils.CronUtils;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.IdUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.TreeUtils;
import com.qcz.qmplatform.module.business.system.domain.Ini;
import com.qcz.qmplatform.module.business.system.domain.Organization;
import com.qcz.qmplatform.module.business.system.domain.assist.IniDefine;
import com.qcz.qmplatform.module.business.system.domain.assist.SyncPeriod;
import com.qcz.qmplatform.module.business.system.domain.assist.SynchroObject;
import com.qcz.qmplatform.module.business.system.domain.dto.OrgCommonConfigDTO;
import com.qcz.qmplatform.module.business.system.domain.dto.SynchroConfigDTO;
import com.qcz.qmplatform.module.business.system.domain.pojo.OrgTree;
import com.qcz.qmplatform.module.business.system.domain.qo.OrganizationQO;
import com.qcz.qmplatform.module.business.system.mapper.OrganizationMapper;
import com.qcz.qmplatform.module.synchro.organization.OrganizationSynchro;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 组织机构服务实现类
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
@Slf4j
@Service
public class OrganizationService extends ServiceImpl<OrganizationMapper, Organization> {

    /**
     * 组织架构同步任务ID
     */
    private static final String SCHEDULE_SYNC_ID = "orgSync";

    @Resource
    private UserOrganizationService userOrganizationService;

    @Resource
    private IniService iniService;

    public List<OrgTree> getOrgList(Organization organization) {
        String organizationId = organization.getOrganizationId();
        String parentId = organization.getParentId();
        Map<String, Object> queryParams = new HashMap<>();
        // 查询当前部门下所有的部门id，包括当前部门
        if (StringUtils.isNotBlank(organizationId)) {
            queryParams.put("notInIds", this.queryOrgIdRecursive(CollectionUtil.newArrayList(organizationId)));
        }
        // 查询当前部门下所有的部门id，不包括当前部门
        if (StringUtils.isNotBlank(parentId)) {
            queryParams.put("inIds", this.queryOrgIdRecursive(CollectionUtil.newArrayList(parentId)));
        }
        return baseMapper.selectOrgTree(queryParams);
    }

    public List<OrgTree> getOrgUserTree(OrganizationQO qo) {
        return TreeUtils.buildTree(baseMapper.selectOrgUserTree(qo));
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
        super.removeByIds(cascOrgIds);
        // 删除部门时，将该部门下的用户转移到未知部门下
        this.updateToUnknownDept(cascOrgIds);
        return true;
    }

    /**
     * 更新到未知部门下
     *
     * @param deptIds 部门ID集合
     */
    public void updateToUnknownDept(List<String> deptIds) {
        String unknownDeptId = getOrgCommonConfig().getUnknownDept();
        unknownDeptId = StringUtils.blankToDefault(unknownDeptId, Constant.ROOT_DEPT_ID);
        userOrganizationService.updateDeptByDeptId(unknownDeptId, deptIds);
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

    /**
     * 根据部门名称获取部门信息
     *
     * @param orgName 部门名称
     */
    public Organization getByName(String orgName) {
        LambdaQueryWrapper<Organization> queryWrapper = Wrappers.lambdaQuery(Organization.class)
                .in(Organization::getOrganizationName, orgName);
        return getOne(queryWrapper);
    }

    /**
     * 保存同步配置及参数
     *
     * @param synchroConfig 同步配置及参数
     */
    public boolean saveSynchroConfig(SynchroConfigDTO synchroConfig) {
        iniService.deleteBySec(IniDefine.SYNCHRO_CONFIG);

        Map<String, Object> map = BeanUtil.beanToMap(synchroConfig);
        List<Ini> iniList = new ArrayList<>();
        for (String iniProperty : map.keySet()) {
            Ini ini = new Ini()
                    .setSection(IniDefine.SYNCHRO_CONFIG)
                    .setProperty(iniProperty)
                    .setValue(String.valueOf(map.get(iniProperty)));
            iniList.add(ini);
        }
        boolean saveBatch = iniService.saveBatch(iniList);
        resetScheduleSync(synchroConfig);
        return saveBatch;
    }

    /**
     * 重置同步任务
     *
     * @param synchroConfig 同步配置参数
     */
    private void resetScheduleSync(SynchroConfigDTO synchroConfig) {
        if (CronUtils.remove(SCHEDULE_SYNC_ID)) {
            log.warn("org sync has removed.");
        }
        scheduleSync(synchroConfig);
    }

    /**
     * 获取同步配置参数
     */
    public SynchroConfigDTO getSynchroConfig() {
        SynchroConfigDTO synchroConfigDTO = new SynchroConfigDTO();
        BeanUtil.copyProperties(iniService.getBySec(IniDefine.SYNCHRO_CONFIG), synchroConfigDTO);
        return synchroConfigDTO;
    }

    /**
     * 立即同步
     *
     * @param synchroConfig 同步配置参数
     */
    public void immediatelySync(SynchroConfigDTO synchroConfig) {
        String syncType = synchroConfig.getSyncType();
        OrganizationSynchro organizationSynchro = SynchroObject.getOrganizationSynchro(syncType);
        organizationSynchro.execute();
    }

    /**
     * 定时同步
     *
     * @param synchroConfig 同步配置参数
     */
    public void scheduleSync(SynchroConfigDTO synchroConfig) {
        if (synchroConfig.getEnableSynchro() != 1) {
            log.warn("org sync is not be enabled, and skipping...");
            return;
        }
        String syncPeriod = synchroConfig.getSyncPeriod();
        String syncDate = synchroConfig.getSyncDate();
        String syncTime = synchroConfig.getSyncTime();
        List<String> syncTimeList = StringUtils.split(syncTime, ":");
        String second = syncTimeList.get(1);
        String minute = syncTimeList.get(0);
        String day = "*";
        String month = "*";
        String week = "*";
        if (StringUtils.equals(syncPeriod, SyncPeriod.WEEK)) {
            day = "?";
            week = StringUtils.subAfter(syncDate, SyncPeriod.WEEK, true);
        } else if (StringUtils.equals(syncPeriod, SyncPeriod.MONTH)) {
            if (StringUtils.equals(syncDate, "dayOfMonthEnd")) {
                // 月末
                day = "L";
                month = "*";
                week = "?";
            } else {
                month = StringUtils.subAfter(syncDate, SyncPeriod.MONTH, true);
            }
        } else {
            week = "?";
        }
        String pattern = StringUtils.format("{} {} {} {} {}", second, minute, day, month, week);
        log.debug("schedule org execute pattern: {}", pattern);
        CronUtils.schedule(SCHEDULE_SYNC_ID, pattern, () -> immediatelySync(synchroConfig));
    }

    /**
     * 更新未知部门
     *
     * @param deptId 部门ID
     */
    public void updateUnknownDept(String deptId) {
        iniService.deleteBySecAndPro(IniDefine.ORG_COMMON_CONFIG, IniDefine.OrgCommonConfig.UNKNOWN_DEPT);
        Ini ini = new Ini()
                .setSection(IniDefine.ORG_COMMON_CONFIG)
                .setProperty(IniDefine.OrgCommonConfig.UNKNOWN_DEPT)
                .setValue(deptId);
        iniService.save(ini);
    }

    /**
     * 获取组织架构通用设置
     */
    public OrgCommonConfigDTO getOrgCommonConfig() {
        OrgCommonConfigDTO orgCommonConfigDTO = new OrgCommonConfigDTO();
        BeanUtil.copyProperties(iniService.getBySec(IniDefine.ORG_COMMON_CONFIG), orgCommonConfigDTO, true);
        return orgCommonConfigDTO;
    }
}
