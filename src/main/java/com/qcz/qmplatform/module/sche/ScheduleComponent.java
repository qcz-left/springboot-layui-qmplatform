package com.qcz.qmplatform.module.sche;

import cn.hutool.cron.CronUtil;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.operation.service.DataBakService;
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
        Map<String, String> dataBak = iniService.getBySec("DataBak");
        int period = Integer.parseInt(dataBak.get("Period"));
        if (StringUtils.equals(dataBak.get("EnableBak"), "1") && period > 0) {
            dataBakService.scheduleBak(period);
        }
        CronUtil.start();
    }
}
