package com.qcz.qmplatform.module.business.operation.service;

import cn.hutool.core.bean.BeanUtil;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.business.operation.domain.dto.LoginSettingDTO;
import com.qcz.qmplatform.module.business.system.domain.Ini;
import com.qcz.qmplatform.module.business.system.domain.assist.IniDefine;
import com.qcz.qmplatform.module.business.system.service.IniService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LoginSettingService {

    @Resource
    private IniService iniService;

    public LoginSettingDTO get() {
        Map<String, String> dataMap = iniService.getBySec(IniDefine.LOGIN_SETTING);
        return BeanUtil.toBean(dataMap, LoginSettingDTO.class);
    }

    public void save(LoginSettingDTO loginSettingDTO) {
        iniService.deleteBySec(IniDefine.LOGIN_SETTING);

        List<Ini> insertIniList = new ArrayList<>();
        Map<String, Object> dataMap = BeanUtil.beanToMap(loginSettingDTO);
        for (String iniKey : dataMap.keySet()) {
            insertIniList.add(
                    new Ini()
                            .setSection(IniDefine.LOGIN_SETTING)
                            .setProperty(iniKey)
                            .setValue(StringUtils.toStringOrNull(dataMap.get(iniKey)))
            );
        }

        iniService.saveBatch(insertIniList);
    }
}
