package com.qcz.qmplatform.module.business.operation.controller;

import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.PageRequest;
import com.qcz.qmplatform.common.bean.PageResult;
import com.qcz.qmplatform.common.bean.PageResultHelper;
import com.qcz.qmplatform.common.bean.PrivCode;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.business.operation.domain.LoginRecord;
import com.qcz.qmplatform.module.business.operation.service.LoginRecordService;
import com.qcz.qmplatform.module.business.operation.domain.vo.LoginStrategyVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.annotation.Resource;
import java.util.Map;

/**
 * 登录记录
 */
@Controller
@RequestMapping("/operation/loginRecord")
@Module("登录错误记录")
public class LoginRecordController extends BaseController {

    @Resource
    LoginRecordService loginRecordService;

    @GetMapping("/loginStrategyPage")
    public String loginStrategyPage(Map<String, Object> root) {
        root.put("loginStrategy", loginRecordService.getStrategy());
        return "/module/operation/loginStrategy";
    }

    @GetMapping("/getLoginStrategy")
    @ResponseBody
    public ResponseResult<LoginStrategyVO> getLoginStrategy() {
        return ResponseResult.ok(loginRecordService.getStrategy());
    }

    @GetMapping("/loginRecordListPage")
    public String loginRecordListPage() {
        return "/module/operation/loginRecordList";
    }

    @PostMapping("/getLoginRecordList")
    @ResponseBody
    public ResponseResult<PageResult<LoginRecord>> getLoginRecordList(PageRequest pageRequest) {
        PageResultHelper.startPage(pageRequest);
        return ResponseResult.ok(PageResultHelper.parseResult(loginRecordService.list()));
    }

    @PostMapping("/saveLoginStrategy")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_LOGIN_STRATEGY_SAVE)
    @RecordLog(type = OperateType.UPDATE, description = "编辑登录策略")
    public ResponseResult<Void> saveLoginStrategy(@RequestBody LoginStrategyVO loginStrategyVO) {
        return ResponseResult.newInstance(loginRecordService.saveLoginStrategy(loginStrategyVO));
    }

    @DeleteMapping("/deleteLoginRecord")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_LOGIN_RECORD_DELETE)
    @RecordLog(type = OperateType.DELETE, description = "删除登录错误记录")
    public ResponseResult<Void> deleteLoginRecord(String recordIds) {
        return ResponseResult.newInstance(loginRecordService.deleteLoginRecord(StringUtils.split(recordIds, ',')));
    }
}
