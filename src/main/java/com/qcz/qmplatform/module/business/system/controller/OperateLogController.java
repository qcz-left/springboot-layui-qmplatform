package com.qcz.qmplatform.module.business.system.controller;


import com.qcz.qmplatform.common.bean.PageRequest;
import com.qcz.qmplatform.common.bean.PageResult;
import com.qcz.qmplatform.common.bean.PageResultHelper;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.business.system.service.OperateLogService;
import com.qcz.qmplatform.module.business.system.domain.qo.OperateLogTimeQO;
import com.qcz.qmplatform.module.business.system.domain.vo.OperateLogVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.annotation.Resource;

/**
 * <p>
 * 操作日志前端控制器
 * </p>
 *
 * @author quchangzhong
 * @since 2020-12-06
 */
@Controller
@RequestMapping("/operate-log")
public class OperateLogController extends BaseController {

    @Resource
    private OperateLogService operateLogService;

    @GetMapping("/logListPage")
    public String logListPage() {
        return "/module/system/operateLogList";
    }

    @PostMapping("/getLogList")
    @ResponseBody
    public ResponseResult<PageResult<OperateLogVO>> getLogList(PageRequest pageRequest, OperateLogTimeQO operateLogTimeQO) {
        PageResultHelper.startPage(pageRequest);
        return ResponseResult.ok(PageResultHelper.parseResult(operateLogService.queryOperateLogList(operateLogTimeQO)));
    }
}
