package com.qcz.qmplatform.module.system.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.utils.CacheUtils;
import com.qcz.qmplatform.common.utils.IdUtils;
import com.qcz.qmplatform.common.utils.SecureUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.system.domain.ThirdpartyApp;
import com.qcz.qmplatform.module.system.mapper.ThirdpartyAppMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 第三方应用参数配置信息 服务实现类
 * </p>
 *
 * @author quchangzhong
 * @since 2022-01-26
 */
@Service
public class ThirdpartyAppService extends ServiceImpl<ThirdpartyAppMapper, ThirdpartyApp> {

    public ThirdpartyApp getOne(String id) {
        ThirdpartyApp thirdpartyApp = getById(id);
        thirdpartyApp.setAppSecret(SecureUtils.aesDecrypt(thirdpartyApp.getAppSecret()));
        return thirdpartyApp;
    }

    public ThirdpartyApp getByName(String name) {
        ThirdpartyApp thirdpartyApp = (ThirdpartyApp) CacheUtils.get(name);
        if (thirdpartyApp == null) {
            QueryWrapper<ThirdpartyApp> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", name);
            thirdpartyApp = getOne(queryWrapper);
            if (thirdpartyApp != null) {
                thirdpartyApp.setAppSecret(SecureUtils.aesDecrypt(thirdpartyApp.getAppSecret()));
                CacheUtils.put(name, thirdpartyApp);
            }
        }

        return thirdpartyApp;
    }

    public boolean saveOne(ThirdpartyApp thirdpartyApp) {
        thirdpartyApp.setId(IdUtils.getUUID());
        thirdpartyApp.setAppSecret(SecureUtils.aesEncrypt(thirdpartyApp.getAppSecret()));
        return save(thirdpartyApp);
    }

    public boolean updateOne(ThirdpartyApp thirdpartyApp) {
        String appSecret = thirdpartyApp.getAppSecret();
        if (SecureUtils.passwordChanged(appSecret)) {
            appSecret = SecureUtils.aesEncrypt(appSecret);
        } else {
            appSecret = null;
        }
        thirdpartyApp.setAppSecret(appSecret);
        return updateById(thirdpartyApp);
    }

    public boolean validateName(String id, String name) {
        QueryWrapper<ThirdpartyApp> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne(StringUtils.isNotBlank(id), "id", id);
        queryWrapper.eq("name", name);
        return CollectionUtil.isEmpty(list(queryWrapper));
    }

    public boolean updateStatus(String id, int status) {
        UpdateWrapper<ThirdpartyApp> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        updateWrapper.set("status", status);
        return update(updateWrapper);
    }
}
