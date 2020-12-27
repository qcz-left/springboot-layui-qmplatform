package com.qcz.qmplatform.module.operation.service;

import cn.hutool.core.util.RuntimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.utils.ConfigLoader;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.FileUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.operation.domain.DataBak;
import com.qcz.qmplatform.module.operation.mapper.DataBakMapper;
import com.qcz.qmplatform.module.operation.vo.DataBakStrategyVO;
import com.qcz.qmplatform.module.system.domain.Ini;
import com.qcz.qmplatform.module.system.service.IniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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

    /**
     * sys_ini配置属性组
     */
    private static final String SECTION = "DataBak";

    @Autowired
    private IniService iniService;

    public boolean saveDataBakStrategy(DataBakStrategyVO dataBakStrategy) {
        int period = dataBakStrategy.getWeek1()
                + dataBakStrategy.getWeek2()
                + dataBakStrategy.getWeek3()
                + dataBakStrategy.getWeek4()
                + dataBakStrategy.getWeek5()
                + dataBakStrategy.getWeek6()
                + dataBakStrategy.getWeek7();
        iniService.deleteBySec(SECTION);
        return iniService.save(new Ini(SECTION, "EnableBak", String.valueOf(dataBakStrategy.getEnable())))
                && iniService.save(new Ini(SECTION, "Period", String.valueOf(period)))
                && iniService.save(new Ini(SECTION, "LimitDiskSpace", String.valueOf(dataBakStrategy.getLimitDiskSpace())));
    }

    /**
     * 数据备份
     */
    public ResponseResult<?> exeBackup() {
        String dataBakPath = ConfigLoader.getDataBakPath();
        File file = new File(dataBakPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        int limitDiskSpace = Integer.parseInt(iniService.getBySecAndPro(SECTION, "LimitDiskSpace"));
        if (file.getTotalSpace() < limitDiskSpace * 1024 * 2024 * 1024L) {
            // 备份文件目录所在磁盘空间不足
            return ResponseResult.error("磁盘空间不足 " + limitDiskSpace + " G，不允许备份！");
        }
        // 备份
        Date date = new Date();
        String bakName = "qmplatform_single" + DateUtils.format(date, "yyyy_MM_dd_hh_mm_ss") + ".dump";
        String bakFilePath = dataBakPath + bakName;
        RuntimeUtil.exec("pg_dump -U postgres qmplatform_single > " + bakFilePath);
        DataBak dataBak = new DataBak();
        dataBak.setBakId(StringUtils.uuid());
        dataBak.setBakName(bakName);
        dataBak.setBakPath(bakFilePath);
        dataBak.setCreateTime(new Timestamp(date.getTime()));
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
        return removeByIds(dataBakIds);
    }
}
