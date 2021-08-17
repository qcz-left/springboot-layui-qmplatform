package com.qcz.qmplatform.module.system.controller;


import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.PrivCode;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.system.domain.Organization;
import com.qcz.qmplatform.module.system.pojo.OrgTree;
import com.qcz.qmplatform.module.system.service.OrganizationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
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

    private static final String PREFIX = "/module/system/";

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/organizationListPage")
    public String departmentListPage() {
        return PREFIX + "organizationList";
    }

    @GetMapping("/orgManagePage")
    public String orgManagePage() {
        return PREFIX + "orgManage";
    }

    @GetMapping("/orgDetailPage")
    public String orgDetailPage(Map<String, Object> root, boolean isDept) {
        root.put("isDept", isDept);
        return PREFIX + "orgDetail";
    }

    @GetMapping("/baseInfoPage")
    public String baseInfoPage(Map<String, Object> root, boolean isDept) {
        root.put("isDept", isDept);
        return PREFIX + "orgBaseInfo";
    }

    @GetMapping("/deptManagePage")
    public String deptManagePage() {
        return PREFIX + "orgDeptManage";
    }

    @GetMapping("/userManagePage")
    public String userManagePage() {
        return PREFIX + "orgUserManage";
    }

    @GetMapping("/organizationDetailPage")
    public String organizationDetailPage() {
        return PREFIX + "organizationDetail";
    }

    @GetMapping("/organizationTreePage")
    public String departmentTreePage() {
        return PREFIX + "organizationTree";
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
     * @param organization 请求参数
     */
    @GetMapping("/getOrgUserTree")
    @ResponseBody
    public ResponseResult<List<OrgTree>> getOrgUserTree(Organization organization) {
        return ResponseResult.ok(organizationService.getOrgUserTree(organization));
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
     * 添加组织机构信息
     *
     * @param org 组织机构信息
     */
    @PostMapping("/addOrg")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_ORG_SAVE)
    @RecordLog(type = OperateType.INSERT, description = "新增组织机构")
    public ResponseResult<?> addOrgOne(@RequestBody Organization org) {
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
    public ResponseResult<?> saveOrgOne(@RequestBody Organization org) {
        if (organizationService.saveOrgOne(org)) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
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
    public ResponseResult<?> updateOrgOne(@RequestBody Organization org) {
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
    public ResponseResult<?> deleteOrg(String orgIds) {
        if (organizationService.deleteOrg(Arrays.asList(orgIds.split(",")))) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
    }

}

