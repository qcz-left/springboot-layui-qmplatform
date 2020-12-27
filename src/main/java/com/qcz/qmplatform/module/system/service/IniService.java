package com.qcz.qmplatform.module.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.system.domain.Ini;
import com.qcz.qmplatform.module.system.mapper.IniMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 系统属性配置 服务实现类
 * </p>
 *
 * @author quchangzhong
 * @since 2020-12-26
 */
@Service
public class IniService extends ServiceImpl<IniMapper, Ini> {

    public Map<String, String> getBySec(String sec) {
        Map<String, String> iniProperty = new HashMap<>();

        QueryWrapper<Ini> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(sec), "section", sec);
        for (Ini ini : list(queryWrapper)) {
            iniProperty.put(ini.getProperty(), ini.getValue());
        }
        return iniProperty;
    }

    public String getBySecAndPro(String sec, String pro) {
        QueryWrapper<Ini> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(sec), "section", sec);
        queryWrapper.eq(StringUtils.isNotBlank(pro), "property", pro);
        return getOne(queryWrapper).getValue();
    }

    public boolean deleteBySec(String sec) {
        QueryWrapper<Ini> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(sec), "section", sec);
        return remove(queryWrapper);
    }

}
