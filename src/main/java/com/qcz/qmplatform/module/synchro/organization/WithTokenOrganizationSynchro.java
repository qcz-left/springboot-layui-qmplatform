package com.qcz.qmplatform.module.synchro.organization;

import com.qcz.qmplatform.common.exception.BusinessException;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.business.system.domain.ThirdpartyApp;
import lombok.extern.slf4j.Slf4j;

/**
 * 基于Token的组织架构同步实现
 *
 * @author quchangzhong
 * @date 2025/1/7
 */
@Slf4j
public abstract class WithTokenOrganizationSynchro extends AbstractOrganizationSynchro {

    /**
     * 接口所需要的 token
     */
    private String accessToken;

    /**
     * 获取Token字符串，会优先从缓存中取
     */
    protected String getAccessToken() {
        if (StringUtils.isNotBlank(accessToken)) {
            return accessToken;
        }

        ThirdpartyApp thirdpartyApp = getThirdpartyApp();
        String appKey = thirdpartyApp.getAppKey();
        String appSecret = thirdpartyApp.getAppSecret();
        accessToken = getAccessTokenByAccount(appKey, appSecret);
        if (StringUtils.isBlank(accessToken)) {
            log.warn("token interface call failure! and recall again");
            // 重试一次
            accessToken = getAccessTokenByAccount(appKey, appSecret);
        }

        if (StringUtils.isBlank(accessToken)) {
            throw new BusinessException("第三方参数错误，请检查");
        }

        return accessToken;
    }

    /**
     * 调用接口获取 AccessToken
     *
     * @param appKey    应用Key
     * @param appSecret 应用密钥
     * @return 接口响应结果
     */
    abstract public String getAccessTokenByAccount(String appKey, String appSecret);

}
