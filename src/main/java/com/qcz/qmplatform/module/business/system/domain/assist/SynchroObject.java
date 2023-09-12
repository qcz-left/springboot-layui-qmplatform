package com.qcz.qmplatform.module.business.system.domain.assist;

import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ReflectUtil;
import com.qcz.qmplatform.module.synchro.organization.OrganizationSynchro;
import com.qcz.qmplatform.module.synchro.organization.dingtalk.DingTalkOrganizationSynchro;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 组织架构同步对象
 */
@Getter
@AllArgsConstructor
public enum SynchroObject {

    /**
     * 钉钉
     */
    DING_TALK("dingtalk-synchro", DingTalkOrganizationSynchro.class);

    String name;

    Class<? extends OrganizationSynchro> clazz;

    /**
     * 获取组织架构同步实例
     *
     * @param name 同步对象名称
     */
    public static OrganizationSynchro getOrganizationSynchro(String name) {
        SynchroObject synchroObject = EnumUtil.getBy(SynchroObject::getName, name);
        return ReflectUtil.newInstance(synchroObject.getClazz());
    }

}
