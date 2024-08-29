package com.qcz.qmplatform.module.business.system.controller;

import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.PageRequest;
import com.qcz.qmplatform.common.bean.PageResult;
import com.qcz.qmplatform.common.bean.PageResultHelper;
import com.qcz.qmplatform.common.bean.PrivCode;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.business.system.domain.Message;
import com.qcz.qmplatform.module.business.system.domain.vo.MessageVO;
import com.qcz.qmplatform.module.business.system.service.MessageService;
import com.qcz.qmplatform.module.watch.DBChangeCenter;
import jakarta.annotation.Resource;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

    @Resource
    MessageService messageService;

    @GetMapping("/messageListPage")
    public String messageListPage() {
        return "/module/system/messageList";
    }

    @PostMapping("/getMessageList")
    @ResponseBody
    public ResponseResult<PageResult<MessageVO>> getMessageList(PageRequest pageRequest, Message message, boolean export) {
        if (!export) {
            PageResultHelper.startPage(pageRequest);
        }

        message.setReceiver(SubjectUtils.getUserId());
        return ResponseResult.ok(PageResultHelper.parseResult(messageService.getList(message)));
    }

    @PostMapping("/setHasRead")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_MESSAGE_SET_READ)
    @RecordLog(type = OperateType.UPDATE, description = "设置已读")
    public ResponseResult<Void> setHasRead(@RequestBody List<String> ids) {
        boolean success = messageService.setHasRead(ids);
        if (success) {
            DBChangeCenter.getInstance().notifyMessage();
        }
        return ResponseResult.newInstance(success);
    }

    @PostMapping("/delete")
    @RequiresPermissions(PrivCode.BTN_CODE_MESSAGE_DELETE)
    @ResponseBody
    @RecordLog(type = OperateType.DELETE, description = "删除系统消息")
    public ResponseResult<Void> delete(@RequestBody List<String> messageIds) {
        boolean success = messageService.removeByIds(messageIds);
        if (success) {
            DBChangeCenter.getInstance().notifyMessage();
        }
        return ResponseResult.newInstance(success);
    }

}
