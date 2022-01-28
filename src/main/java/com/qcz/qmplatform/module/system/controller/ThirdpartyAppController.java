package com.qcz.qmplatform.module.system.controller;

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
import com.qcz.qmplatform.module.sync.DBChangeCenter;
import com.qcz.qmplatform.module.sync.DBNotifyInfo;
import com.qcz.qmplatform.module.system.domain.ThirdpartyApp;
import com.qcz.qmplatform.module.system.service.ThirdpartyAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    private static final String PREFIX = "/module/system/";

    @Autowired
    ThirdpartyAppService thirdpartyAppService;

    @GetMapping("/thirdpartyAppListPage")
    public String thirdpartyAppListPage() {
        return PREFIX + "thirdpartyAppList";
    }

    @GetMapping("/thirdpartyAppDetailPage")
    public String thirdpartyAppDetailPage() {
        return PREFIX + "thirdpartyAppDetail";
    }

    @PostMapping("/getThirdpartyAppList")
    @ResponseBody
    public ResponseResult<PageResult> getThirdpartyAppList(PageRequest pageRequest) {
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
    public ResponseResult<?> insert(@RequestBody ThirdpartyApp thirdpartyApp) {
        boolean success = thirdpartyAppService.saveOne(thirdpartyApp);
        DBChangeCenter.getInstance().doNotify(DBNotifyInfo.newInstance(DBProperties.Table.SYS_THIRDPARTY_APP, thirdpartyApp.getName()));
        return ResponseResult.newInstance(success);
    }

    @PostMapping("/update")
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "修改第三方应用参数配置信息")
    public ResponseResult<?> update(@RequestBody ThirdpartyApp thirdpartyApp) {
        boolean success = thirdpartyAppService.updateOne(thirdpartyApp);
        DBChangeCenter.getInstance().doNotify(DBNotifyInfo.newInstance(DBProperties.Table.SYS_THIRDPARTY_APP, thirdpartyApp.getName()));
        return ResponseResult.newInstance(success);
    }

    @PostMapping("/delete")
    @ResponseBody
    @RecordLog(type = OperateType.DELETE, description = "删除第三方应用参数配置信息")
    public ResponseResult<?> delete(String ids, String names) {
        boolean success = thirdpartyAppService.removeByIds(StringUtils.split(ids, ','));
        DBChangeCenter.getInstance().doNotify(DBNotifyInfo.newInstance(DBProperties.Table.SYS_THIRDPARTY_APP, names));
        return ResponseResult.newInstance(success);
    }

    @GetMapping("/validateName")
    @ResponseBody
    public ResponseResult<?> validateName(String id, String name) {
        return ResponseResult.newInstance(thirdpartyAppService.validateName(id, name));
    }
}
