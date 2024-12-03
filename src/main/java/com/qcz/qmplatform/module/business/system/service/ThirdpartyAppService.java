package com.qcz.qmplatform.module.business.system.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.bean.DBProperties;
import com.qcz.qmplatform.common.utils.CacheUtils;
import com.qcz.qmplatform.common.utils.IdUtils;
import com.qcz.qmplatform.common.utils.SecureUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.business.system.domain.ThirdpartyApp;
import com.qcz.qmplatform.module.business.system.domain.dto.ThirdpartyAppDeleteDTO;
import com.qcz.qmplatform.module.business.system.mapper.ThirdpartyAppMapper;
import com.qcz.qmplatform.module.watch.DBChangeCenter;
import com.qcz.qmplatform.module.watch.DBNotifyInfo;
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

    /**
     * 通过id查询详情
     *
     * @param id 第三方系统参数id
     */
    public ThirdpartyApp getOne(String id) {
        ThirdpartyApp thirdpartyApp = getById(id);
        thirdpartyApp.setAppSecret(SecureUtils.aesDecrypt(thirdpartyApp.getAppSecret()));
        return thirdpartyApp;
    }

    /**
     * 通过名称查询详情
     *
     * @param name 第三方系统参数名称
     */
    public ThirdpartyApp getByName(String name) {
        ThirdpartyApp thirdpartyApp = (ThirdpartyApp) CacheUtils.get(name);
        if (thirdpartyApp == null) {
            LambdaQueryWrapper<ThirdpartyApp> queryWrapper = Wrappers.lambdaQuery(ThirdpartyApp.class)
                    .eq(ThirdpartyApp::getName, name);
            thirdpartyApp = getOne(queryWrapper);
            if (thirdpartyApp != null) {
                thirdpartyApp.setAppSecret(SecureUtils.aesDecrypt(thirdpartyApp.getAppSecret()));
                CacheUtils.put(name, thirdpartyApp);
            }
        }

        return thirdpartyApp;
    }

    /**
     * 新增一条数据
     */
    public boolean saveOne(ThirdpartyApp thirdpartyApp) {
        thirdpartyApp.setId(IdUtils.getUUID());
        thirdpartyApp.setAppSecret(SecureUtils.aesEncrypt(thirdpartyApp.getAppSecret()));
        boolean success = save(thirdpartyApp);
        if (success) {
            dbChange(thirdpartyApp.getName());
        }
        return success;
    }

    /**
     * 更新一条数据
     */
    public boolean updateOne(ThirdpartyApp thirdpartyApp) {
        String appSecret = thirdpartyApp.getAppSecret();
        if (SecureUtils.passwordChanged(appSecret)) {
            appSecret = SecureUtils.aesEncrypt(appSecret);
        } else {
            appSecret = null;
        }
        thirdpartyApp.setAppSecret(appSecret);
        boolean success = updateById(thirdpartyApp);
        if (success) {
            dbChange(thirdpartyApp.getName());
        }
        return success;
    }

    /**
     * 删除一条数据
     */
    public boolean removeOne(ThirdpartyAppDeleteDTO dto) {
        boolean success = super.removeById(dto.getId());
        if (success) {
            dbChange(dto.getName());
        }
        return success;
    }

    /**
     * 校验名称
     *
     * @param id   参数id
     * @param name 第三方参数名称
     * @return 不存在则校验通过
     */
    public boolean validateName(String id, String name) {
        LambdaQueryWrapper<ThirdpartyApp> queryWrapper = Wrappers.lambdaQuery(ThirdpartyApp.class)
                .ne(StringUtils.isNotBlank(id), ThirdpartyApp::getId, id)
                .eq(ThirdpartyApp::getName, name);
        return CollectionUtil.isEmpty(list(queryWrapper));
    }

    /**
     * 更新状态（已废除）
     *
     * @param thirdpartyApp 第三方系统参数
     */
    @Deprecated
    public boolean updateStatus(ThirdpartyApp thirdpartyApp) {
        LambdaUpdateWrapper<ThirdpartyApp> updateWrapper = Wrappers.lambdaUpdate(ThirdpartyApp.class)
                .eq(ThirdpartyApp::getId, thirdpartyApp.getId())
                .set(ThirdpartyApp::getStatus, thirdpartyApp.getStatus());
        boolean success = update(updateWrapper);
        if (success) {
            dbChange(thirdpartyApp.getName());
        }
        return success;
    }

    /**
     * 数据库通知
     *
     * @param name 第三方系统名称
     */
    private void dbChange(String name) {
        DBChangeCenter.getInstance().doNotify(DBNotifyInfo.newInstance(DBProperties.Table.SYS_THIRDPARTY_APP, name));
    }

}
