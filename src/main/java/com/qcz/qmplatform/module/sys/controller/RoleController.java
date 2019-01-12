package com.qcz.qmplatform.module.sys.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qcz.qmplatform.common.base.BaseController;
import com.qcz.qmplatform.common.message.PageRequest;
import com.qcz.qmplatform.common.message.PageResult;
import com.qcz.qmplatform.common.message.PageResultHelper;
import com.qcz.qmplatform.common.message.ResponseResult;
import com.qcz.qmplatform.common.utils.HttpServletUtils;
import com.qcz.qmplatform.module.sys.entity.Role;
import com.qcz.qmplatform.module.sys.service.RoleService;

/**
 * 角色 Controller
 * @author changzhongq
 * @time 2018年6月11日 下午5:10:04
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController<Role, RoleService> {

    private static final String PATH_PREFIX = "/module/sys/role/";

    @RequestMapping("/roleListPage")
    public String roleListPage() {
        return PATH_PREFIX + "roleList";
    }

    @RequestMapping("/roleForm")
    public String roleForm() {
        return PATH_PREFIX + "roleForm";
    }

    @RequestMapping("/chooseRolePage")
    public String chooseRolePage() {
        return PATH_PREFIX + "chooseRole";
    }

    /**
     * 绑定角色
     * @param userId 用户ID
     * @param roles  要绑定的角色ID
     */
    @RequestMapping("/bindRole")
    @ResponseBody
    public ResponseResult bindRole(String userId, String[] roles) {
        logger.info("the called method : bindRole");
        service.bindRole(userId, roles);
        return ResponseResult.ok("绑定成功！", null);
    }

    /**
     * 根据用户id获取用户角色关系
     * @param userId 用户ID
     */
    @RequestMapping("/getRoleByUserId")
    @ResponseBody
    public PageResult getRoleByUserId(String userId) {
        logger.info("the called method : getRoleByUserId");
        return PageResultHelper.parseResult(service.getRoleByUserId(userId));
    }

    @RequestMapping("/roleList")
    @ResponseBody
    public PageResult roleList(HttpServletRequest request, PageRequest pageRequest) {
        Map<String, Object> paramMap = HttpServletUtils.parseRequestByParam(request);
        PageResultHelper.startPage(pageRequest);
        return PageResultHelper.parseResult(service.findRoleList(paramMap));
    }

    @RequestMapping("/roleListWithStatus")
    @ResponseBody
    public PageResult roleListWithStatus(HttpServletRequest request, PageRequest pageRequest) {
        Map<String, Object> paramMap = HttpServletUtils.parseRequestByParam(request);
        return PageResultHelper.parseResult(service.findRoleListWithStatus(paramMap));
    }

    /**
     * 保存角色基础信息和绑定菜单信息
     * @param data    基础信息
     * @param menuIds 菜单
     */
    @RequestMapping(value = "/saveRole", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult saveRole(Role data, String menuIds) {
        return ResponseResult.ok("保存成功！", service.saveRole(data, menuIds));
    }
}
