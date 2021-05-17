package com.qcz.qmplatform.module.sche;

import cn.hutool.cron.CronUtil;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.operation.service.DataBakService;
import com.qcz.qmplatform.module.system.assist.IniDefine;
import com.qcz.qmplatform.module.system.service.IniService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 定时任务组件
 */
@Component
public class ScheduleComponent implements InitializingBean {

    @Autowired
    DataBakService dataBakService;

    @Autowired
    IniService iniService;

    @Override
    public void afterPropertiesSet() {
        // 数据库备份
        Map<String, String> dataBak = iniService.getBySec(IniDefine.DATA_BAK);
        int period = Integer.parseInt(dataBak.get(IniDefine.DataBak.PERIOD));
        if (StringUtils.equals(dataBak.get(IniDefine.DataBak.ENABLE_BAK), "1") && period > 0) {
            dataBakService.scheduleBak(period);
        }
        CronUtil.start();
    }
}
