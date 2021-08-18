package com.qcz.qmplatform.module.operation.controller;

import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.PageRequest;
import com.qcz.qmplatform.common.bean.PageResult;
import com.qcz.qmplatform.common.bean.PageResultHelper;
import com.qcz.qmplatform.common.bean.PrivCode;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.operation.service.LoginRecordService;
import com.qcz.qmplatform.module.operation.vo.LoginStrategyVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;

/**
 * 登录记录
 */
@Controller
@RequestMapping("/operation/loginRecord")
@Module("登录错误记录")
public class LoginRecordController extends BaseController {

    private static final String PREFIX = "/module/operation/";

    @Autowired
    LoginRecordService loginRecordService;

    @GetMapping("/loginStrategyPage")
    public String loginStrategyPage(Map<String, Object> root) {
        root.put("loginStrategy", loginRecordService.getStrategy());
        return PREFIX + "loginStrategy";
    }

    @GetMapping("/getLoginStrategy")
    @ResponseBody
    public ResponseResult<LoginStrategyVO> getLoginStrategy() {
        return ResponseResult.ok(loginRecordService.getStrategy());
    }

    @GetMapping("/loginRecordListPage")
    public String loginRecordListPage() {
        return PREFIX + "loginRecordList";
    }

    @PostMapping("/getLoginRecordList")
    @ResponseBody
    public ResponseResult<PageResult> getLoginRecordList(PageRequest pageRequest) {
        PageResultHelper.startPage(pageRequest);
        return ResponseResult.ok(PageResultHelper.parseResult(loginRecordService.list()));
    }

    @PostMapping("/saveLoginStrategy")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_LOGIN_STRATEGY_SAVE)
    @RecordLog(type = OperateType.UPDATE, description = "编辑登录策略")
    public ResponseResult<?> saveLoginStrategy(@RequestBody LoginStrategyVO loginStrategyVO) {
        if (loginRecordService.saveLoginStrategy(loginStrategyVO)) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
    }

    @DeleteMapping("/deleteLoginRecord")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_LOGIN_RECORD_DELETE)
    @RecordLog(type = OperateType.DELETE, description = "删除登录错误记录")
    public ResponseResult<?> deleteLoginRecord(String recordIds) {
        if (loginRecordService.deleteLoginRecord(Arrays.asList(recordIds.split(",")))) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
    }
}
