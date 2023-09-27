package com.qcz.qmplatform.module.business.system.controller;


import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.PrivCode;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.business.system.domain.Organization;
import com.qcz.qmplatform.module.business.system.domain.dto.OrgCommonConfigDTO;
import com.qcz.qmplatform.module.business.system.domain.dto.SynchroConfigDTO;
import com.qcz.qmplatform.module.business.system.domain.pojo.OrgTree;
import com.qcz.qmplatform.module.business.system.domain.qo.OrganizationQO;
import com.qcz.qmplatform.module.business.system.service.OrganizationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 组织机构前端控制器
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
@Controller
@RequestMapping("/organization")
@Module("组织机构管理")
public class OrganizationController extends BaseController {

    @Resource
    private OrganizationService organizationService;

    @GetMapping("/organizationListPage")
    public String departmentListPage() {
        return "/module/system/organizationList";
    }

    @GetMapping("/orgManagePage")
    public String orgManagePage() {
        return "/module/system/orgManage";
    }

    @GetMapping("/orgDetailPage")
    public String orgDetailPage(Map<String, Object> root, boolean isDept) {
        root.put("isDept", isDept);
        return "/module/system/orgDetail";
    }

    @GetMapping("/baseInfoPage")
    public String baseInfoPage(Map<String, Object> root, boolean isDept) {
        root.put("isDept", isDept);
        return "/module/system/orgBaseInfo";
    }

    @GetMapping("/deptManagePage")
    public String deptManagePage() {
        return "/module/system/orgDeptManage";
    }

    @GetMapping("/userManagePage")
    public String userManagePage() {
        return "/module/system/orgUserManage";
    }

    @GetMapping("/organizationDetailPage")
    public String organizationDetailPage() {
        return "/module/system/organizationDetail";
    }

    /**
     * 选择部门弹窗
     */
    @GetMapping("/chooseDept")
    public String chooseDept() {
        return "/module/system/chooseDept";
    }

    /**
     * 组织架构同步设置页面
     */
    @GetMapping("/synchroPage")
    public String synchroPage() {
        return "/module/system/orgSynchro";
    }

    /**
     * 获取组织机构列表
     *
     * @param organization 请求参数
     */
    @PostMapping("/getOrgList")
    @ResponseBody
    public ResponseResult<List<OrgTree>> getOrgList(Organization organization) {
        return ResponseResult.ok(organizationService.getOrgList(organization));
    }

    /**
     * 获取组织机构树形数据
     *
     * @param organization 请求参数
     */
    @GetMapping("/getOrgTree")
    @ResponseBody
    public ResponseResult<List<OrgTree>> getOrgTree(Organization organization) {
        return ResponseResult.ok(organizationService.getOrgTree(organization));
    }

    /**
     * 获取组织机构
     *
     * @param qo 请求参数
     */
    @GetMapping("/getOrgUserTree")
    @ResponseBody
    public ResponseResult<List<OrgTree>> getOrgUserTree(OrganizationQO qo) {
        return ResponseResult.ok(organizationService.getOrgUserTree(qo));
    }

    /**
     * 获取组织机构信息
     *
     * @param orgId 组织机构id
     */
    @GetMapping("/getOrgOne/{orgId}")
    @ResponseBody
    public ResponseResult<Organization> getOrgOne(@PathVariable String orgId) {
        return ResponseResult.ok(organizationService.getById(orgId));
    }

    /**
     * 获取同步配置
     */
    @PostMapping("/getSynchroConfig")
    @ResponseBody
    public ResponseResult<SynchroConfigDTO> getSynchroConfig() {
        return ResponseResult.ok(organizationService.getSynchroConfig());
    }

    /**
     * 添加组织机构信息
     *
     * @param org 组织机构信息
     */
    @PostMapping("/addOrg")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_ORG_SAVE)
    @RecordLog(type = OperateType.INSERT, description = "新增组织机构")
    public ResponseResult<Void> addOrgOne(@RequestBody Organization org) {
        return saveOrgOne(org);
    }

    /**
     * 保存组织机构信息（新增或修改）
     *
     * @param org 组织机构信息
     */
    @PostMapping("/saveOrgOne")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_ORG_SAVE)
    public ResponseResult<Void> saveOrgOne(@RequestBody Organization org) {
        return ResponseResult.newInstance(organizationService.saveOrgOne(org));
    }

    /**
     * 修改组织机构信息
     *
     * @param org 组织机构信息
     */
    @PutMapping("/updateOrg")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_ORG_SAVE)
    @RecordLog(type = OperateType.UPDATE, description = "修改组织机构")
    public ResponseResult<Void> updateOrgOne(@RequestBody Organization org) {
        return saveOrgOne(org);
    }

    /**
     * 删除组织机构信息
     *
     * @param orgIds 组织机构id数组
     */
    @DeleteMapping("/deleteOrg")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_ORG_DELETE)
    @RecordLog(type = OperateType.DELETE, description = "删除组织机构")
    public ResponseResult<Void> deleteOrg(String orgIds) {
        return ResponseResult.newInstance(organizationService.deleteOrg(StringUtils.split(orgIds, ',')));
    }

    /**
     * 保存组织架构同步配置
     */
    @PostMapping("/saveSynchroConfig")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_ORG_SAVE)
    @RecordLog(type = OperateType.UPDATE, description = "保存组织架构同步配置")
    public ResponseResult<Void> saveSynchroConfig(@RequestBody SynchroConfigDTO synchroConfigDTO) {
        return ResponseResult.newInstance(organizationService.saveSynchroConfig(synchroConfigDTO));
    }

    /**
     * 立即同步组织架构
     */
    @PostMapping("/immediatelySync")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_ORG_SAVE)
    @RecordLog(type = OperateType.UPDATE, description = "立即同步组织架构")
    public ResponseResult<Void> immediatelySync(@RequestBody SynchroConfigDTO synchroConfigDTO) {
        organizationService.immediatelySync(synchroConfigDTO);
        return ResponseResult.ok();
    }

    /**
     * 更新未知部门
     *
     * @param deptId 部门ID
     */
    @PostMapping("/updateUnknownDept/{deptId}")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_ORG_SAVE)
    @RecordLog(type = OperateType.UPDATE, description = "更新未知部门")
    public ResponseResult<Void> updateUnknownDept(@PathVariable("deptId") String deptId) {
        organizationService.updateUnknownDept(deptId);
        return ResponseResult.ok();
    }

    /**
     * 获取组织架构通用设置
     */
    @PostMapping("/getOrgCommonConfig")
    @ResponseBody
    public ResponseResult<OrgCommonConfigDTO> getOrgCommonConfig() {
        return ResponseResult.ok(organizationService.getOrgCommonConfig());
    }

}

