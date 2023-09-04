package com.qcz.qmplatform.module.business.operation.controller;

import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.module.business.operation.domain.dto.LoginSettingDTO;
import com.qcz.qmplatform.module.business.operation.service.LoginSettingService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/operation/loginSetting")
@Module("登录设置")
public class LoginSettingController {

    @Resource
    private LoginSettingService loginSettingService;

    @GetMapping("/index")
    public String index() {
        return "/module/operation/loginSetting";
    }

    @GetMapping("/detail")
    public String detail() {
        return "/module/operation/loginSettingDetail";
    }

    /**
     * 获取登录设置信息
     */
    @GetMapping("/get")
    @ResponseBody
    public ResponseResult<LoginSettingDTO> get() {
        return ResponseResult.ok(loginSettingService.get());
    }

    /**
     * 保存登录设置
     *
     * @param loginSettingDTO 登录设置参数
     */
    @PostMapping("/save")
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "保存登录设置")
    public ResponseResult<Void> save(@RequestBody @Validated LoginSettingDTO loginSettingDTO) {
        loginSettingService.save(loginSettingDTO);
        return ResponseResult.ok();
    }
}
