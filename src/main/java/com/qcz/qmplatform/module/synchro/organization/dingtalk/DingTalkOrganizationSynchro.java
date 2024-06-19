package com.qcz.qmplatform.module.synchro.organization.dingtalk;

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
import com.qcz.qmplatform.module.synchro.organization.dingtalk.bean.DingTalkAccessToken;
import com.qcz.qmplatform.module.synchro.organization.dingtalk.bean.DingTalkDeptResponse;
import com.qcz.qmplatform.module.synchro.organization.dingtalk.bean.DingTalkDeptResult;
import com.qcz.qmplatform.module.synchro.organization.dingtalk.bean.DingTalkUserResponse;
import com.qcz.qmplatform.module.synchro.organization.dingtalk.bean.DingTalkUserResult;
import com.qcz.qmplatform.module.synchro.organization.dingtalk.bean.DingTalkUserResultItem;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 钉钉组织架构同步
 */
@Slf4j
public class DingTalkOrganizationSynchro implements OrganizationSynchro {

    /**
     * 钉钉接口网址
     */
    private final static String DING_TALK_ENDPOINT = "https://oapi.dingtalk.com";

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
        return getDeptListByParentId(null);
    }

    @Override
    public List<User> getUserListByDeptId(String deptId) {
        return getUserListByDeptId(deptId, 0);
    }

    @Override
    public void refreshConfig() {
        thirdpartyApp = SpringContextUtils.getBean(ThirdpartyAppService.class).getByName(SynchroObject.DING_TALK.getName());
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
     * 查询用户列表
     *
     * @param deptId 部门ID
     * @param cursor 查询游标，首次传入0
     */
    public List<User> getUserListByDeptId(String deptId, int cursor) {
        String userApiUrl = DING_TALK_ENDPOINT + "/topapi/v2/user/list?access_token=" + accessToken;
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("dept_id", deptId);
        bodyMap.put("cursor", cursor);
        bodyMap.put("size", 100);
        String userResult = HttpUtil.post(userApiUrl, JSONUtil.toJsonStr(bodyMap));
        DingTalkUserResponse userResponse = JSONUtil.toBean(userResult, DingTalkUserResponse.class);
        int errcode = userResponse.getErrcode();
        String errmsg = userResponse.getErrmsg();
        if (errcode != 0) {
            log.warn("user interface call failure with deptId: {}! error code: {}, error msg: {}", deptId, errcode, errmsg);
            return CollectionUtils.newArrayList();
        }

        List<User> allUserList = new ArrayList<>();
        DingTalkUserResult result = userResponse.getResult();
        boolean hasMore = result.isHas_more();
        int nextCursor = result.getNext_cursor();
        List<DingTalkUserResultItem> resultList = result.getList();
        for (DingTalkUserResultItem resultItem : resultList) {
            String name = resultItem.getName();
            User user = new User()
                    .setId(resultItem.getUserid())
                    .setPassword(SecureUtils.accountEncrypt(SecureUtils.DEFAULT_PASSWORD))
                    .setPhone(resultItem.getMobile())
                    .setEmailAddr(resultItem.getEmail())
                    .setRemark(resultItem.getRemark())
                    .setUsername(name);
            allUserList.add(user);

            if (hasMore) {
                allUserList.addAll(getUserListByDeptId(deptId, nextCursor));
            }
        }
        return allUserList;
    }

    private List<Organization> getDeptListByParentId(Long parentId) {
        String deptApiUrl = DING_TALK_ENDPOINT + "/topapi/v2/department/listsub?access_token=" + accessToken;
        Map<String, Object> bodyMap = new HashMap<>();
        if (Objects.nonNull(parentId)) {
            bodyMap.put("dept_id", parentId);
        }
        String deptResult = HttpUtil.post(deptApiUrl, JSONUtil.toJsonStr(bodyMap));
        DingTalkDeptResponse deptResponse = JSONUtil.toBean(deptResult, DingTalkDeptResponse.class);
        int errcode = deptResponse.getErrcode();
        String errmsg = deptResponse.getErrmsg();
        if (errcode != 0) {
            log.warn("dept interface call failure with dept parentId: {}! error code: {}, error msg: {}", parentId, errcode, errmsg);
            return CollectionUtils.newArrayList();
        }

        List<Organization> allDeptList = new ArrayList<>();
        List<DingTalkDeptResult> result = deptResponse.getResult();
        for (DingTalkDeptResult resultItem : result) {
            long deptId = resultItem.getDept_id();
            String name = resultItem.getName();
            Organization organization = new Organization()
                    .setOrganizationId(String.valueOf(deptId))
                    .setOrganizationName(name)
                    .setParentId(String.valueOf(resultItem.getParent_id()))
                    .setOrganizationCode(PinyinUtil.getPinyin(name, ""));
            allDeptList.add(organization);

            List<Organization> deptList = getDeptListByParentId(deptId);
            allDeptList.addAll(deptList);
        }
        return allDeptList;
    }

    @Override
    public String getRootId() {
        return "1";
    }

    private String getAccessToken() {
        String accessToken = null;

        String appKey = thirdpartyApp.getAppKey();
        String appSecret = thirdpartyApp.getAppSecret();
        DingTalkAccessToken dingTalkAccessToken = getDingTalkAccessToken(appKey, appSecret);
        if (dingTalkAccessToken.getErrcode() != 0) {
            log.warn("token interface call failure! and recall again");
            // 重试一次
            DingTalkAccessToken dingTalkAccessTokenAgain = getDingTalkAccessToken(appKey, appSecret);
            if (dingTalkAccessTokenAgain.getErrcode() == 0) {
                accessToken = dingTalkAccessTokenAgain.getAccess_token();
            }
        } else {
            accessToken = dingTalkAccessToken.getAccess_token();
        }

        return accessToken;
    }

    /**
     * 调用接口获取 AccessToken
     *
     * @param appKey    基础信息中应用的appKey
     * @param appSecret 基础信息中应用的appSecret
     * @return 接口响应结果
     */
    private DingTalkAccessToken getDingTalkAccessToken(String appKey, String appSecret) {
        String tokenApiUrl = DING_TALK_ENDPOINT + "/gettoken?appkey=" + appKey + "&appsecret=" + appSecret;
        String tokenResult = HttpUtil.get(tokenApiUrl);
        log.debug("the response result of getDingTalkAccessToken: " + tokenResult);
        return JSONUtil.toBean(tokenResult, DingTalkAccessToken.class);
    }

}
