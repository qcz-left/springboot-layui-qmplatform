package com.qcz.qmplatform.module.synchro.organization.wechat;

import cn.hutool.extra.pinyin.PinyinUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.qcz.qmplatform.common.exception.BusinessException;
import com.qcz.qmplatform.common.utils.CollectionUtils;
import com.qcz.qmplatform.common.utils.SecureUtils;
import com.qcz.qmplatform.common.utils.SpringContextUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.business.system.domain.Organization;
import com.qcz.qmplatform.module.business.system.domain.ThirdpartyApp;
import com.qcz.qmplatform.module.business.system.domain.User;
import com.qcz.qmplatform.module.business.system.domain.assist.SynchroObject;
import com.qcz.qmplatform.module.business.system.domain.dto.SynchroConfigDTO;
import com.qcz.qmplatform.module.business.system.service.OrganizationService;
import com.qcz.qmplatform.module.business.system.service.ThirdpartyAppService;
import com.qcz.qmplatform.module.synchro.organization.OrganizationSynchro;
import com.qcz.qmplatform.module.synchro.organization.wechat.bean.WorkWechatAccessToken;
import com.qcz.qmplatform.module.synchro.organization.wechat.bean.WorkWechatDeptListResponse;
import com.qcz.qmplatform.module.synchro.organization.wechat.bean.WorkWechatDeptResult;
import com.qcz.qmplatform.module.synchro.organization.wechat.bean.WorkWechatUserListResponse;
import com.qcz.qmplatform.module.synchro.organization.wechat.bean.WorkWechatUserResult;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 企业微信组织架构同步
 */
@Slf4j
public class WorkWechatOrganizationSynchro implements OrganizationSynchro {

    /**
     * 企业微信接口网址
     */
    private final static String WORK_WECHAT_ENDPOINT = "https://qyapi.weixin.qq.com";

    /**
     * 接口所需要的 token
     */
    private String accessToken;

    /**
     * 第三方配置参数
     */
    private ThirdpartyApp thirdpartyApp;

    /**
     * 同步设置
     */
    private SynchroConfigDTO synchroConfig;

    @Override
    public List<Organization> getDeptList() {
        String deptApiUrl = WORK_WECHAT_ENDPOINT + "/cgi-bin/department/list?access_token=" + accessToken + "&id=" + getRootId();
        String deptResult = HttpUtil.get(deptApiUrl);
        WorkWechatDeptListResponse deptResponse = JSONUtil.toBean(deptResult, WorkWechatDeptListResponse.class);
        int errcode = deptResponse.getErrcode();
        String errmsg = deptResponse.getErrmsg();
        if (errcode != 0) {
            log.warn("dept list interface call failure! error code: {}, error msg: {}", errcode, errmsg);
            return CollectionUtils.newArrayList();
        }

        List<Organization> organizationList = new ArrayList<>();
        List<WorkWechatDeptResult> deptResultList = deptResponse.getDepartment();
        for (WorkWechatDeptResult workWechatDeptResult : deptResultList) {
            String deptId = String.valueOf(workWechatDeptResult.getId());
            String departmentName = workWechatDeptResult.getName();
            int order = workWechatDeptResult.getOrder();
            String parentId = String.valueOf(workWechatDeptResult.getParentid());

            Organization organization = new Organization();
            organization.setOrganizationId(deptId)
                    .setOrganizationName(departmentName)
                    .setParentId(parentId)
                    .setOrganizationCode(PinyinUtil.getPinyin(departmentName, ""))
                    .setIorder(order);

            organizationList.add(organization);
        }
        return organizationList;
    }

    @Override
    public List<User> getUserListByDeptId(String deptId) {
        String userApiUrl = WORK_WECHAT_ENDPOINT + "/cgi-bin/user/list?access_token=" + accessToken + "&department_id=" + deptId;
        String userResult = HttpUtil.get(userApiUrl);
        WorkWechatUserListResponse userResponse = JSONUtil.toBean(userResult, WorkWechatUserListResponse.class);
        int errcode = userResponse.getErrcode();
        String errmsg = userResponse.getErrmsg();
        if (errcode != 0) {
            log.warn("user list interface call failure with deptId: {}! error code: {}, error msg: {}", deptId, errcode, errmsg);
            return CollectionUtils.newArrayList();
        }

        List<User> userList = new ArrayList<>();
        List<WorkWechatUserResult> userResultList = userResponse.getUserlist();
        for (WorkWechatUserResult workWechatUserResult : userResultList) {
            String name = workWechatUserResult.getName();
            User user = new User()
                    .setId(workWechatUserResult.getUserid())
                    .setPassword(SecureUtils.accountEncrypt(SecureUtils.DEFAULT_PASSWORD))
                    .setPhone(workWechatUserResult.getMobile())
                    .setEmailAddr(workWechatUserResult.getEmail())
                    .setUsername(name)
                    .setUserSex(workWechatUserResult.getGender());

            userList.add(user);
        }
        return userList;
    }

    @Override
    public String getRootId() {
        return "1";
    }

    @Override
    public void refreshConfig() {
        thirdpartyApp = SpringContextUtils.getBean(ThirdpartyAppService.class).getByName(SynchroObject.WORK_WECHAT.getName());
        if (Objects.isNull(thirdpartyApp)) {
            throw new BusinessException("未设置第三方参数");
        }
        accessToken = getAccessToken();
        if (StringUtils.isBlank(accessToken)) {
            throw new BusinessException("第三方参数错误，请检查");
        }
        synchroConfig = SpringContextUtils.getBean(OrganizationService.class).getSynchroConfig();
    }

    @Override
    public String getLoginNameSyncWay() {
        return synchroConfig.getLoginNameSyncWay();
    }

    /**
     * 获取Token字符串
     */
    private String getAccessToken() {
        String accessToken = null;

        String appKey = thirdpartyApp.getAppKey();
        String appSecret = thirdpartyApp.getAppSecret();
        WorkWechatAccessToken workWechatAccessToken = getWorkWechatAccessToken(appKey, appSecret);
        if (workWechatAccessToken.getErrcode() != 0) {
            log.warn("token interface call failure! and recall again");
            // 重试一次
            WorkWechatAccessToken workWechatAccessTokenAgain = getWorkWechatAccessToken(appKey, appSecret);
            if (workWechatAccessTokenAgain.getErrcode() == 0) {
                accessToken = workWechatAccessTokenAgain.getAccess_token();
            }
        } else {
            accessToken = workWechatAccessToken.getAccess_token();
        }

        return accessToken;
    }

    /**
     * 调用接口获取 AccessToken
     *
     * @param corpId     企业ID
     * @param corpSecret 应用的凭证密钥
     * @return 接口响应结果
     */
    private WorkWechatAccessToken getWorkWechatAccessToken(String corpId, String corpSecret) {
        String tokenApiUrl = WORK_WECHAT_ENDPOINT + "/cgi-bin/gettoken?corpid=" + corpId + "&corpsecret=" + corpSecret;
        String tokenResult = HttpUtil.get(tokenApiUrl);
        log.debug("the response result of getWorkWechatAccessToken: " + tokenResult);
        return JSONUtil.toBean(tokenResult, WorkWechatAccessToken.class);
    }
}
