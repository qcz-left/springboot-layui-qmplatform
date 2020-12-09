package com.qcz.qmplatform.module.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qcz.qmplatform.common.bean.PageRequest;
import com.qcz.qmplatform.common.bean.PageResult;
import com.qcz.qmplatform.common.bean.PageResultHelper;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.system.domain.OperateLog;
import com.qcz.qmplatform.module.system.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;

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
public class OperateLogController {

    private static final String PREFIX = "/module/system/";

    @Autowired
    private OperateLogService operateLogService;

    @GetMapping("/logListPage")
    public String logListPage() {
        return PREFIX + "operateLogList";
    }

    @GetMapping("/getLogList")
    @ResponseBody
    public ResponseResult<PageResult> getLogList(PageRequest pageRequest, OperateLog log, String operateTime_start, String operateTime_end) {
        QueryWrapper<OperateLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(log.getOperateType() != null, "operate_type", log.getOperateType());
        queryWrapper.eq(log.getOperateStatus() != null, "operate_status", log.getOperateStatus());
        if (StringUtils.isNotBlank(operateTime_start)) {
            queryWrapper.ge("operate_time", Timestamp.valueOf(operateTime_start));
        }
        if (StringUtils.isNotBlank(operateTime_end)) {
            queryWrapper.lt("operate_time", Timestamp.valueOf(operateTime_end));
        }
        PageResultHelper.startPage(pageRequest);
        return ResponseResult.ok(PageResultHelper.parseResult(operateLogService.list(queryWrapper)));
    }
}
