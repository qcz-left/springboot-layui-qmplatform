package com.qcz.qmplatform.module.operation.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.RuntimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.exception.CommonException;
import com.qcz.qmplatform.common.utils.ConfigLoader;
import com.qcz.qmplatform.common.utils.CronUtils;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.FileUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.SystemUtils;
import com.qcz.qmplatform.module.operation.domain.DataBak;
import com.qcz.qmplatform.module.operation.mapper.DataBakMapper;
import com.qcz.qmplatform.module.operation.vo.DataBakStrategyVO;
import com.qcz.qmplatform.module.system.domain.Ini;
import com.qcz.qmplatform.module.system.service.IniService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据备份 服务实现类
 * </p>
 *
 * @author quchangzhong
 * @since 2020-12-26
 */
@Service
public class DataBakService extends ServiceImpl<DataBakMapper, DataBak> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataBakService.class);

    /**
     * sys_ini配置属性组
     */
    private static final String SECTION = "DataBak";

    private static final String SCHEDULE_ID = "dataBak";

    @Value("${custom.database}")
    private String database;

    @Autowired
    private IniService iniService;

    public boolean saveDataBakStrategy(DataBakStrategyVO dataBakStrategy) {
        int week1 = dataBakStrategy.getWeek1();
        int week2 = dataBakStrategy.getWeek2();
        int week3 = dataBakStrategy.getWeek3();
        int week4 = dataBakStrategy.getWeek4();
        int week5 = dataBakStrategy.getWeek5();
        int week6 = dataBakStrategy.getWeek6();
        int week7 = dataBakStrategy.getWeek7();
        int period = week1 + week2 + week3 + week4 + week5 + week6 + week7;
        CronUtils.remove(SCHEDULE_ID);
        if (dataBakStrategy.getEnable() == 1 && period > 0) {
            scheduleBak(period);
        }

        iniService.deleteBySec(SECTION);
        return iniService.save(new Ini(SECTION, "EnableBak", String.valueOf(dataBakStrategy.getEnable())))
                && iniService.save(new Ini(SECTION, "Period", String.valueOf(period)))
                && iniService.save(new Ini(SECTION, "LimitDiskSpace", String.valueOf(dataBakStrategy.getLimitDiskSpace())))
                && iniService.save(new Ini(SECTION, "SaveDays", String.valueOf(dataBakStrategy.getSaveDays())));
    }

    public void scheduleBak(int period) {
        List<String> periodList = new ArrayList<>();
        // 每个周期的上午1点执行
        String pattern = "0 1 ? * ";
        if ((period & 64) == 64) {// 周天
            periodList.add("1");
        }
        if ((period & 1) == 1) {// 周一
            periodList.add("2");
        }
        if ((period & 2) == 2) {
            periodList.add("3");
        }
        if ((period & 4) == 4) {
            periodList.add("4");
        }
        if ((period & 8) == 8) {
            periodList.add("5");
        }
        if ((period & 16) == 16) {
            periodList.add("6");
        }
        if ((period & 32) == 32) {
            periodList.add("7");
        }
        pattern += CollectionUtil.join(periodList, ",");
        CronUtils.schedule(SCHEDULE_ID, pattern, () -> LOGGER.debug("schedule data bak exe result[{}]: {}", DateUtils.now(), exeBackup("系统自动备份")));
    }

    /**
     * 数据备份
     *
     * @param bakRemark 备份描述
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public ResponseResult<?> exeBackup(String bakRemark) {
        if (!SystemUtils.OS.isLinux()) {
            throw new CommonException("非Linux环境不允许备份数据！");
        }
        String dataBakPath = ConfigLoader.getDataBakPath();
        File file = new File(dataBakPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        Map<String, String> bakStrategy = iniService.getBySec(SECTION);
        int limitDiskSpace = Integer.parseInt(bakStrategy.get("LimitDiskSpace"));
        if (file.getTotalSpace() < limitDiskSpace * 1024 * 2024 * 1024L) {
            // 备份文件目录所在磁盘空间不足
            return ResponseResult.error("磁盘空间不足 " + limitDiskSpace + " G，不允许备份！");
        }
        // 删除 day 天前的备份
        String saveDays = bakStrategy.get("SaveDays");
        if (StringUtils.isNotBlank(saveDays)) {
            QueryWrapper<DataBak> dataBakQueryWrapper = new QueryWrapper<>();
            long beforeDaySeconds = DateUtils.currentSeconds() - Integer.parseInt(saveDays) * 24 * 60 * 60;
            dataBakQueryWrapper.le("create_time", DateUtils.timestamp(beforeDaySeconds * 1000L));
            List<DataBak> needDelDataBakes = list(dataBakQueryWrapper);
            // 删除数据库记录和对应备份文件
            for (DataBak needDelDataBake : needDelDataBakes) {
                FileUtils.del(needDelDataBake.getBakPath());
            }
            remove(dataBakQueryWrapper);
        }
        // 备份
        Date date = new Date();
        String bakName = database + DateUtils.format(date, DatePattern.PURE_DATETIME_PATTERN) + ".dump";
        String bakFilePath = dataBakPath + bakName;

        String dumpCmd = StringUtils.format("pg_dump -U postgres -Fc {} -f {}", database, bakFilePath);
        LOGGER.debug("dump exe shell: " + dumpCmd);
        LOGGER.debug(RuntimeUtil.execForStr(dumpCmd));

        DataBak dataBak = new DataBak();
        dataBak.setBakId(StringUtils.uuid());
        dataBak.setBakName(bakName);
        dataBak.setBakPath(bakFilePath);
        dataBak.setCreateTime(DateUtils.timestamp(date));
        dataBak.setFileSize(FileUtils.size(FileUtils.file(bakFilePath)));
        dataBak.setRemark(bakRemark);
        if (save(dataBak)) {
            return ResponseResult.ok();
        } else {
            FileUtils.del(bakFilePath);
            return ResponseResult.error();
        }
    }

    public boolean deleteDataBak(List<String> dataBakIds) {
        QueryWrapper<DataBak> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("bak_id", dataBakIds);
        List<DataBak> dataBakes = list(queryWrapper);
        for (DataBak dataBake : dataBakes) {
            FileUtils.del(dataBake.getBakPath());
        }
        return removeByIds(dataBakIds);
    }

    public ResponseResult<?> recoverDataBak(String dataBakId) {
        DataBak dataBak = getById(dataBakId);
        String bakPath = dataBak.getBakPath();
        if (!new File(bakPath).exists()) {
            return ResponseResult.error("备份文件不存在！");
        }
        String recoverSh = FileUtils.WEB_PATH + "/shell/db_bak_recover.sh";
        if (!new File(recoverSh).exists()) {
            return ResponseResult.error("备份恢复所需脚本文件缺失！");
        }
        LOGGER.debug(RuntimeUtil.execForStr(StringUtils.format("{} {} {}", recoverSh, bakPath, database)));
        return ResponseResult.ok();
    }
}
