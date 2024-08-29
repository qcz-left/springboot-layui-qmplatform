package com.qcz.qmplatform.module.business.system.controller;


import com.qcz.qmplatform.common.bean.PageRequest;
import com.qcz.qmplatform.common.bean.PageResult;
import com.qcz.qmplatform.common.bean.PageResultHelper;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.business.system.domain.Button;
import com.qcz.qmplatform.module.business.system.service.ButtonService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
@Controller
@RequestMapping("/button")
public class ButtonController extends BaseController {

    @Resource
    private ButtonService buttonService;

    /**
     * 获取按钮列表
     *
     * @param pageRequest 分页请求
     * @param button      请求参数
     */
    @PostMapping("/getButtonList")
    @ResponseBody
    public ResponseResult<PageResult<Button>> getButtonList(PageRequest pageRequest, Button button) {
        PageResultHelper.startPage(pageRequest.getPage(), pageRequest.getLimit());
        List<Button> buttonList = buttonService.getButtonList(button);
        return ResponseResult.ok(PageResultHelper.parseResult(buttonList));
    }

    /**
     * 获取按钮信息
     *
     * @param buttonId 按钮id
     * @return ResponseResult<Button>
     */
    @GetMapping("/getButtonOne/{buttonId}")
    @ResponseBody
    public ResponseResult<Button> getButtonOne(@PathVariable String buttonId) {
        return ResponseResult.ok(buttonService.getById(buttonId));
    }

    /**
     * 添加按钮信息
     *
     * @param button 按钮表单信息
     * @return ResponseResult
     */
    @PostMapping("/addButtonOne")
    @ResponseBody
    public ResponseResult<Void> addButtonOne(@RequestBody Button button) {
        return ResponseResult.newInstance(buttonService.addButtonOne(button));
    }

}

