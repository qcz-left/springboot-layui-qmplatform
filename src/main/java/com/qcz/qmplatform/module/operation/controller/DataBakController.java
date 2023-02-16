package com.qcz.qmplatform.module.operation.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.PageRequest;
import com.qcz.qmplatform.common.bean.PageResult;
import com.qcz.qmplatform.common.bean.PageResultHelper;
import com.qcz.qmplatform.common.bean.PrivCode;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.operation.domain.DataBak;
import com.qcz.qmplatform.module.operation.service.DataBakService;
import com.qcz.qmplatform.module.operation.vo.DataBakStrategyVO;
import com.qcz.qmplatform.module.operation.vo.DataBakVO;
import com.qcz.qmplatform.module.system.assist.IniDefine;
import com.qcz.qmplatform.module.system.service.IniService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * <p>
 * 数据备份 前端控制器
 * </p>
 *
 * @author quchangzhong
 * @since 2020-12-26
 */
@Controller
@RequestMapping("/operation/data-bak")
@Module("数据备份与恢复")
public class DataBakController extends BaseController {

    private static final String PREFIX = "/module/operation/";

    /**
     * sys_ini配置属性组
     */
    private static final String SECTION = IniDefine.DATA_BAK;

    @Autowired
    private DataBakService dataBakService;

    @Autowired
    private IniService iniService;

    @GetMapping("/dataBakListPage")
    public String dataBakListPage() {
        return PREFIX + "dataBakList";
    }

    @GetMapping("/dataBakStrategyPage")
    public String dataBakStrategyPage() {
        return PREFIX + "dataBakStrategy";
    }

    @PostMapping("/getDataBakList")
    @ResponseBody
    public ResponseResult<PageResult> getDataBakList(PageRequest pageRequest, DataBakVO dataBakVO) {
        PageResultHelper.startPage(pageRequest);
        LambdaQueryWrapper<DataBak> queryWrapper = Wrappers.lambdaQuery(DataBak.class)
                .like(StringUtils.isNotBlank(dataBakVO.getBakName()), DataBak::getBakName, dataBakVO.getBakName());
        return ResponseResult.ok(PageResultHelper.parseResult(dataBakService.list(queryWrapper)));
    }

    @GetMapping("/getDataBakStrategy")
    @ResponseBody
    public ResponseResult<DataBakStrategyVO> getDataBakStrategy() {
        DataBakStrategyVO strategy = new DataBakStrategyVO();
        Map<String, String> iniPro = iniService.getBySec(SECTION);
        if (CollectionUtil.isNotEmpty(iniPro)) {
            String enableBak = iniPro.get(IniDefine.DataBak.ENABLE_BAK);
            strategy.setEnable(StringUtils.isBlank(enableBak) ? 0 : Integer.parseInt(enableBak));
            String saveDays = iniPro.get(IniDefine.DataBak.SAVE_DAYS);
            strategy.setSaveDays(StringUtils.isBlank(enableBak) ? 0 : Integer.parseInt(saveDays));
            String periodStr = iniPro.get(IniDefine.DataBak.PERIOD);
            int period = Integer.parseInt(periodStr);
            strategy.setPeriod(period);
            strategy.setWeek1(period & 1);
            strategy.setWeek2(period & 2);
            strategy.setWeek3(period & 4);
            strategy.setWeek4(period & 8);
            strategy.setWeek5(period & 16);
            strategy.setWeek6(period & 32);
            strategy.setWeek7(period & 64);
            String limitDiskSpace = iniPro.get(IniDefine.DataBak.LIMIT_DISK_SPACE);
            strategy.setLimitDiskSpace(StringUtils.isBlank(limitDiskSpace) ? 20 : Integer.parseInt(limitDiskSpace));
        }
        return ResponseResult.ok(strategy);
    }

    /**
     * 保存备份策略
     */
    @PostMapping("/saveDataBakStrategy")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_DATA_BAK_STRATEGY_SAVE)
    @RecordLog(type = OperateType.UPDATE, description = "调整数据备份策略")
    public ResponseResult<?> saveDataBakStrategy(@RequestBody DataBakStrategyVO dataBakStrategyVO) {
        return ResponseResult.newInstance(dataBakService.saveDataBakStrategy(dataBakStrategyVO));
    }

    /**
     * 执行备份操作
     */
    @PostMapping("/exeBackup")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_DATA_BAK_SAVE)
    @RecordLog(type = OperateType.INSERT, description = "立即备份")
    public ResponseResult<?> exeBackup(@RequestBody DataBak dataBak) {
        return dataBakService.exeBackup(dataBak.getRemark(), SubjectUtils.getUserId());
    }

    /**
     * 恢复备份
     */
    @PostMapping("/recoverDataBak/{dataBakId}")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_DATA_BAK_RECOVER)
    public ResponseResult<?> recoverDataBak(@PathVariable String dataBakId) {
        return dataBakService.recoverDataBak(dataBakId);
    }

    @DeleteMapping("/deleteDataBak")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_DATA_BAK_DELETE)
    @RecordLog(type = OperateType.DELETE, description = "删除备份")
    public ResponseResult<?> deleteDataBak(String dataBakIds) {
        return ResponseResult.newInstance(dataBakService.deleteDataBak(StringUtils.split(dataBakIds, ',')));
    }
}
