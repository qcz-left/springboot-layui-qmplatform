package com.qcz.qmplatform.module.synchro.organization;

import cn.hutool.core.util.EnumUtil;
import com.qcz.qmplatform.common.exception.BusinessException;
import com.qcz.qmplatform.common.utils.SpringContextUtils;
import com.qcz.qmplatform.module.business.system.domain.ThirdpartyApp;
import com.qcz.qmplatform.module.business.system.domain.assist.SynchroObject;
import com.qcz.qmplatform.module.business.system.domain.dto.SynchroConfigDTO;
import com.qcz.qmplatform.module.business.system.service.OrganizationService;
import com.qcz.qmplatform.module.business.system.service.ThirdpartyAppService;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 组织架构同步抽象实现
 *
 * @author quchangzhong
 * @date 2025/1/7
 */
@Getter
@Setter
public abstract class AbstractOrganizationSynchro implements OrganizationSynchro {

    /**
     * 同步设置
     */
    private SynchroConfigDTO synchroConfig;

    /**
     * 第三方配置参数
     */
    private ThirdpartyApp thirdpartyApp;

    /**
     * 标记同步任务是否正在运行，只允许一个任务运行
     */
    private static boolean running = false;

    private ReentrantLock runningLock = new ReentrantLock();

    @Override
    public void refreshConfig() {
        thirdpartyApp = SpringContextUtils.getBean(ThirdpartyAppService.class).getByName(synchroObjectName());
        if (Objects.isNull(thirdpartyApp)) {
            throw new BusinessException("未设置第三方参数");
        }
        synchroConfig = SpringContextUtils.getBean(OrganizationService.class).getSynchroConfig();
    }

    @Override
    public String getLoginNameSyncWay() {
        return synchroConfig.getLoginNameSyncWay();
    }

    @Override
    public String synchroObjectName() {
        return EnumUtil.getBy(SynchroObject::getClazz, getClass()).getName();
    }

    @Override
    public void executeCheck() {
        runningLock.lock();
        try {
            if (running) {
                throw new BusinessException("同步任务正在执行，请勿重复添加！");
            }
            running = true;
        } finally {
            runningLock.unlock();
        }
    }

    @Override
    public void executeFinished() {
        running = false;
    }

}
