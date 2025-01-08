package com.qcz.qmplatform.module.synchro.organization.wechat;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.qcz.qmplatform.common.utils.SecureUtils;
import com.qcz.qmplatform.module.business.system.domain.Organization;
import com.qcz.qmplatform.module.business.system.domain.User;
import com.qcz.qmplatform.module.synchro.organization.WithTokenOrganizationSynchro;
import com.qcz.qmplatform.module.synchro.organization.wechat.bean.WorkWechatAccessToken;
import com.qcz.qmplatform.module.synchro.organization.wechat.bean.WorkWechatDeptListResponse;
import com.qcz.qmplatform.module.synchro.organization.wechat.bean.WorkWechatDeptResult;
import com.qcz.qmplatform.module.synchro.organization.wechat.bean.WorkWechatUserListResponse;
import com.qcz.qmplatform.module.synchro.organization.wechat.bean.WorkWechatUserResult;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 企业微信组织架构同步
 */
@Slf4j
public class WorkWechatOrganizationSynchro extends WithTokenOrganizationSynchro {

    /**
     * 企业微信接口网址
     */
    private final static String WORK_WECHAT_ENDPOINT = "https://qyapi.weixin.qq.com";

    @Override
    public List<Organization> getDeptList() {
        String deptApiUrl = WORK_WECHAT_ENDPOINT + "/cgi-bin/department/list?access_token=" + getAccessToken() + "&id=" + getRootId();
        String deptResult = HttpUtil.get(deptApiUrl);
        WorkWechatDeptListResponse deptResponse = JSONUtil.toBean(deptResult, WorkWechatDeptListResponse.class);
        int errcode = deptResponse.getErrcode();
        String errmsg = deptResponse.getErrmsg();
        if (errcode != 0) {
            log.warn("dept list interface call failure! error code: {}, error msg: {}", errcode, errmsg);
            return CollectionUtil.newArrayList();
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
        String userApiUrl = WORK_WECHAT_ENDPOINT + "/cgi-bin/user/list?access_token=" + getAccessToken() + "&department_id=" + deptId;
        String userResult = HttpUtil.get(userApiUrl);
        WorkWechatUserListResponse userResponse = JSONUtil.toBean(userResult, WorkWechatUserListResponse.class);
        int errcode = userResponse.getErrcode();
        String errmsg = userResponse.getErrmsg();
        if (errcode != 0) {
            log.warn("user list interface call failure with deptId: {}! error code: {}, error msg: {}", deptId, errcode, errmsg);
            return CollectionUtil.newArrayList();
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
    public String getAccessTokenByAccount(String appKey, String appSecret) {
        String tokenApiUrl = WORK_WECHAT_ENDPOINT + "/cgi-bin/gettoken?corpid=" + appKey + "&corpsecret=" + appSecret;
        String tokenResult = HttpUtil.get(tokenApiUrl);
        log.debug("the response result of getWorkWechatAccessToken: " + tokenResult);
        return JSONUtil.toBean(tokenResult, WorkWechatAccessToken.class).getAccess_token();
    }

}
