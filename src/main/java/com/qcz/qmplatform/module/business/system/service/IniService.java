package com.qcz.qmplatform.module.business.system.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.business.system.domain.Ini;
import com.qcz.qmplatform.module.business.system.mapper.IniMapper;
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

        LambdaQueryWrapper<Ini> queryWrapper = Wrappers.lambdaQuery(Ini.class)
                .eq(StringUtils.isNotBlank(sec), Ini::getSection, sec);
        for (Ini ini : list(queryWrapper)) {
            iniProperty.put(ini.getProperty(), ini.getValue());
        }
        return iniProperty;
    }

    public String getBySecAndPro(String sec, String pro) {
        LambdaQueryWrapper<Ini> queryWrapper = Wrappers.lambdaQuery(Ini.class)
                .eq(StringUtils.isNotBlank(sec), Ini::getSection, sec)
                .eq(StringUtils.isNotBlank(pro), Ini::getProperty, pro);
        return getOne(queryWrapper).getValue();
    }

    public void deleteBySecAndPro(String sec, String pro) {
        Wrapper<Ini> wrapper = Wrappers.lambdaQuery(Ini.class)
                .eq(StringUtils.isNotBlank(sec), Ini::getSection, sec)
                .eq(StringUtils.isNotBlank(pro), Ini::getProperty, pro);
        remove(wrapper);
    }

    public boolean deleteBySec(String sec) {
        LambdaQueryWrapper<Ini> queryWrapper = Wrappers.lambdaQuery(Ini.class)
                .eq(StringUtils.isNotBlank(sec), Ini::getSection, sec);
        return remove(queryWrapper);
    }

}
