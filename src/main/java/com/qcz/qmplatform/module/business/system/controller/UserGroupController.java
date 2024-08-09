package com.qcz.qmplatform.module.business.system.controller;

import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.business.system.domain.UserGroup;
import com.qcz.qmplatform.module.business.system.domain.pojo.UserGroupTree;
import com.qcz.qmplatform.module.business.system.domain.vo.UserGroupVO;
import com.qcz.qmplatform.module.business.system.service.UserGroupService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户组 前端控制器
 * </p>
 *
 * @author quchangzhong
 * @since 2023-08-28
 */
@Controller
@RequestMapping("/system/user-group")
@Module("用户组")
public class UserGroupController extends BaseController {

    @Resource
    private UserGroupService userGroupService;

    @GetMapping("index")
    public String index() {
        return "/module/system/userGroupIndex";
    }

    @GetMapping("userGroupDetailPage")
    public String userGroupDetailPage(Map<String, Object> root, String nodeId) {
        root.put("isInsert", StringUtils.isBlank(nodeId));
        return "/module/system/userGroupDetail";
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseResult<UserGroupVO> get(@PathVariable String id) {
        return ResponseResult.ok(userGroupService.getById(id));
    }

    @PostMapping("/getUserGroupTree")
    @ResponseBody
    public ResponseResult<List<UserGroupTree>> getUserGroupTree() {
        return ResponseResult.ok(userGroupService.getUserGroupTree());
    }

    @PostMapping("/insert")
    @ResponseBody
    @RecordLog(type = OperateType.INSERT, description = "新增用户组")
    public ResponseResult<Void> insert(@RequestBody UserGroup userGroup) {
        return ResponseResult.newInstance(userGroupService.save(userGroup));
    }

    @PostMapping("/update")
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "修改用户组")
    public ResponseResult<Void> update(@RequestBody UserGroup userGroup) {
        return ResponseResult.newInstance(userGroupService.updateById(userGroup));
    }

    @PostMapping("/delete")
    @ResponseBody
    @RecordLog(type = OperateType.DELETE, description = "删除用户组")
    public ResponseResult<Void> delete(@RequestBody List<String> ids) {
        return ResponseResult.newInstance(userGroupService.removeByIds(ids));
    }
}
