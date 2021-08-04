package com.qcz.qmplatform.module.system.controller;

import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.PageRequest;
import com.qcz.qmplatform.common.bean.PageResult;
import com.qcz.qmplatform.common.bean.PageResultHelper;
import com.qcz.qmplatform.common.bean.PrivCode;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.sync.DBChangeCenter;
import com.qcz.qmplatform.module.system.domain.Message;
import com.qcz.qmplatform.module.system.service.MessageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;

/**
 * <p>
 * 系统消息 前端控制器
 * </p>
 *
 * @author quchangzhong
 * @since 2021-03-26
 */
@Controller
@RequestMapping("/system/message")
@Module("系统消息")
public class MessageController extends BaseController {

    private static final String PREFIX = "/module/system/";

    @Autowired
    MessageService messageService;

    @GetMapping("/messageListPage")
    public String messageListPage() {
        return PREFIX + "messageList";
    }

    @RequestMapping("/getMessageList")
    @ResponseBody
    public ResponseResult<PageResult> getMessageList(PageRequest pageRequest, Message message, boolean export) {
        if (!export) {
            PageResultHelper.startPage(pageRequest);
        }

        return ResponseResult.ok(PageResultHelper.parseResult(messageService.getList(message)));
    }

    @PutMapping("/setHasRead")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_MESSAGE_SET_READ)
    @RecordLog(type = OperateType.UPDATE, description = "设置已读")
    public ResponseResult<?> setHasRead(@RequestBody Map<String, String[]> params) {
        if (!messageService.setHasRead(params.get("messageIds"))) {
            return ResponseResult.error();
        }
        dbChanged();
        return ResponseResult.ok();
    }

    @DeleteMapping("/delete")
    @RequiresPermissions(PrivCode.BTN_CODE_MESSAGE_DELETE)
    @ResponseBody
    @RecordLog(type = OperateType.DELETE, description = "删除系统消息")
    public ResponseResult<?> delete(String messageIds) {
        if (!messageService.removeByIds(Arrays.asList(messageIds.split(",")))) {
            return ResponseResult.error();
        }
        dbChanged();
        return ResponseResult.ok();
    }

    private void dbChanged() {
        DBChangeCenter.getInstance().doNotify("sys_message");
    }
}
