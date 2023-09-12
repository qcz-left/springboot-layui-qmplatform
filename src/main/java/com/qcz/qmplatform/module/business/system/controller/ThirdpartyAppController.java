package com.qcz.qmplatform.module.business.system.controller;

import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.DBProperties;
import com.qcz.qmplatform.common.bean.PageRequest;
import com.qcz.qmplatform.common.bean.PageResult;
import com.qcz.qmplatform.common.bean.PageResultHelper;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.watch.DBChangeCenter;
import com.qcz.qmplatform.module.watch.DBNotifyInfo;
import com.qcz.qmplatform.module.business.system.domain.ThirdpartyApp;
import com.qcz.qmplatform.module.business.system.service.ThirdpartyAppService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * <p>
 * 第三方应用参数配置信息 前端控制器
 * </p>
 *
 * @author quchangzhong
 * @since 2022-01-26
 */
@Controller
@RequestMapping("/system/thirdparty-app")
@Module("第三方应用参数配置信息")
public class ThirdpartyAppController extends BaseController {

    @Resource
    ThirdpartyAppService thirdpartyAppService;

    @GetMapping("/thirdpartyAppListPage")
    public String thirdpartyAppListPage() {
        return "/module/system/thirdpartyAppList";
    }

    @GetMapping("/thirdpartyAppDetailPage")
    public String thirdpartyAppDetailPage() {
        return "/module/system/thirdpartyAppDetail";
    }

    @PostMapping("/getThirdpartyAppList")
    @ResponseBody
    public ResponseResult<PageResult<ThirdpartyApp>> getThirdpartyAppList(PageRequest pageRequest) {
        PageResultHelper.startPage(pageRequest);
        return ResponseResult.ok(PageResultHelper.parseResult(thirdpartyAppService.list()));
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseResult<ThirdpartyApp> get(@PathVariable String id) {
        return ResponseResult.ok(thirdpartyAppService.getOne(id));
    }

    @PostMapping("/insert")
    @ResponseBody
    @RecordLog(type = OperateType.INSERT, description = "新增第三方应用参数配置信息")
    public ResponseResult<Void> insert(@RequestBody ThirdpartyApp thirdpartyApp) {
        boolean success = thirdpartyAppService.saveOne(thirdpartyApp);
        DBChangeCenter.getInstance().doNotify(DBNotifyInfo.newInstance(DBProperties.Table.SYS_THIRDPARTY_APP, thirdpartyApp.getName()));
        return ResponseResult.newInstance(success);
    }

    @PostMapping("/update")
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "修改第三方应用参数配置信息")
    public ResponseResult<Void> update(@RequestBody ThirdpartyApp thirdpartyApp) {
        boolean success = thirdpartyAppService.updateOne(thirdpartyApp);
        DBChangeCenter.getInstance().doNotify(DBNotifyInfo.newInstance(DBProperties.Table.SYS_THIRDPARTY_APP, thirdpartyApp.getName()));
        return ResponseResult.newInstance(success);
    }

    @PostMapping("/delete")
    @ResponseBody
    @RecordLog(type = OperateType.DELETE, description = "删除第三方应用参数配置信息")
    public ResponseResult<Void> delete(String id, String name) {
        boolean success = thirdpartyAppService.removeByIds(StringUtils.split(id, ','));
        DBChangeCenter.getInstance().doNotify(DBNotifyInfo.newInstance(DBProperties.Table.SYS_THIRDPARTY_APP, name));
        return ResponseResult.newInstance(success);
    }

    @GetMapping("/validateName")
    @ResponseBody
    public ResponseResult<Void> validateName(String id, String name) {
        return ResponseResult.newInstance(thirdpartyAppService.validateName(id, name));
    }

    @PostMapping("/updateStatus")
    @ResponseBody
    @Deprecated
    public ResponseResult<Void> updateStatus(@RequestBody ThirdpartyApp thirdpartyApp) {
        boolean success = thirdpartyAppService.updateStatus(thirdpartyApp.getId(), thirdpartyApp.getStatus());
        DBChangeCenter.getInstance().doNotify(DBNotifyInfo.newInstance(DBProperties.Table.SYS_THIRDPARTY_APP, thirdpartyApp.getName()));
        return ResponseResult.newInstance(success);
    }
}
