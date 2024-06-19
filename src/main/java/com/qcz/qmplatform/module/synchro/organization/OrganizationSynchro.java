package com.qcz.qmplatform.module.synchro.organization;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.qcz.qmplatform.common.constant.Constant;
import com.qcz.qmplatform.common.utils.CollectionUtils;
import com.qcz.qmplatform.common.utils.SpringContextUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.business.system.domain.Organization;
import com.qcz.qmplatform.module.business.system.domain.User;
import com.qcz.qmplatform.module.business.system.domain.UserOrganization;
import com.qcz.qmplatform.module.business.system.service.OrganizationService;
import com.qcz.qmplatform.module.business.system.service.UserOrganizationService;
import com.qcz.qmplatform.module.business.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 组织架构同步接口
 */
public interface OrganizationSynchro {

    Logger LOGGER = LoggerFactory.getLogger(OrganizationSynchro.class);

    /**
     * 获取部门列表
     */
    List<Organization> getDeptList();

    /**
     * 根据部门ID获取用户列表
     *
     * @param deptId 部门ID
     */
    List<User> getUserListByDeptId(String deptId);

    /**
     * 第三方根部门ID
     */
    String getRootId();

    /**
     * 转换根部门ID为系统根部门ID
     */
    default String covertRootDeptId(String rootId) {
        if (StringUtils.equals(getRootId(), rootId)) {
            return Constant.ROOT_DEPT_ID;
        }
        return rootId;
    }

    /**
     * 刷新同步设置，每次同步前会自动执行一次
     */
    default void refreshConfig() {

    }

    /**
     * 登录名同步方式<br>
     * usernamePinyin: 用户名全拼音，默认此项<br>
     * emailPrefix: 邮箱前缀<br>
     */
    default String getLoginNameSyncWay() {
        return "usernamePinyin";
    }

    /**
     * 执行同步
     */
    @Transactional
    default void execute() {
        LOGGER.info("starting sync organization and user to system.");
        long startTimeMillis = System.currentTimeMillis();

        refreshConfig();
        String loginNameSyncWay = StringUtils.blankToDefault(getLoginNameSyncWay(), "");
        LOGGER.info("loginNameSyncWay: {}", loginNameSyncWay);

        UserService userService = SpringContextUtils.getBean(UserService.class);
        OrganizationService organizationService = SpringContextUtils.getBean(OrganizationService.class);
        UserOrganizationService userOrganizationService = SpringContextUtils.getBean(UserOrganizationService.class);

        // 现有数据库中已存在的数据，用来做对比
        List<Organization> allOrganizationList = organizationService.list();
        // 部门名->部门ID映射
        Map<String, String> deptNameIdMap = new HashMap<>();
        for (Organization organization : allOrganizationList) {
            deptNameIdMap.put(organization.getOrganizationName(), organization.getOrganizationId());
        }

        List<User> allUserList = userService.list();
        // 记录所有登录名
        Set<String> allLoginNames = new HashSet<>();
        // 用户名->用户ID映射
        Map<String, String> userNameIdMap = new HashMap<>();
        for (User user : allUserList) {
            userNameIdMap.put(user.getUsername(), user.getId());
            allLoginNames.add(user.getLoginname());
        }

        List<String> deptIds = new ArrayList<>();
        List<Organization> deptList = getDeptList();
        List<Organization> updateDeptList = new ArrayList<>();
        List<Organization> insertDeptList = new ArrayList<>();
        for (Organization organization : deptList) {
            String organizationId = organization.getOrganizationId();
            String organizationName = organization.getOrganizationName();
            String oldId = deptNameIdMap.get(organizationName);
            boolean equalsId = StringUtils.equals(oldId, organizationId);
            if (deptNameIdMap.containsKey(organizationName) && !equalsId) {
                // 同名但ID不一样，说明该部门不是通过同步过来的，跳过
                LOGGER.warn("[dept name: {}]id is not equal, old id: {}, new id{}, and skipping.", organizationName, oldId, organizationId);
                continue;
            }
            deptIds.add(organizationId);
            organization.setParentId(covertRootDeptId(organization.getParentId()));
            if (equalsId) {
                updateDeptList.add(organization);
            } else {
                insertDeptList.add(organization);
            }
        }

        if (CollectionUtils.isNotEmpty(updateDeptList)) {
            organizationService.updateBatchById(updateDeptList);
            LOGGER.info("sync update dept finish, size: {}", updateDeptList.size());
        }
        if (CollectionUtils.isNotEmpty(insertDeptList)) {
            organizationService.saveBatch(insertDeptList);
            LOGGER.info("sync insert dept finish, size: {}", insertDeptList.size());
        }

        deptIds.add(getRootId());
        List<User> updateUserList = new ArrayList<>();
        List<User> insertUserList = new ArrayList<>();
        List<UserOrganization> userOrganizationList = new ArrayList<>();
        // 需要删除的用户部门关联ID（采用先删除后插入的方式）
        List<String> deleteUserIdByUserOrg = new ArrayList<>();
        for (String deptId : deptIds) {
            List<User> userList = getUserListByDeptId(deptId);
            for (User user : userList) {
                String userId = user.getId();
                String username = user.getUsername();
                String oldId = userNameIdMap.get(username);
                boolean equalsId = StringUtils.equals(oldId, userId);
                if (userNameIdMap.containsKey(username) && !equalsId) {
                    // 同名但ID不一样，说明该用户不是通过同步过来的，跳过
                    LOGGER.warn("[user name: {}]id is not equal, old id: {}, new id{}, and skipping...", username, oldId, userId);
                    continue;
                }
                // 设置登录名
                String loginName = user.getLoginname();
                String emailAddr = user.getEmailAddr();
                switch (loginNameSyncWay) {
                    case "usernamePinyin":
                        loginName = PinyinUtil.getPinyin(username, "");
                        break;
                    case "emailPrefix":
                        if (StringUtils.isNotBlank(emailAddr)) {
                            loginName = StringUtils.subPre(emailAddr, emailAddr.indexOf("@"));
                        }
                        break;
                }

                if (allLoginNames.contains(loginName) && !equalsId) {
                    LOGGER.warn("login name: {} has exists, and skipping...", loginName);
                    continue;
                }

                if (StringUtils.isBlank(loginName)) {
                    LOGGER.warn("[user name: {}]login name is blank, and skipping...", username);
                    continue;
                }
                allLoginNames.add(loginName);
                user.setLoginname(loginName);

                if (equalsId) {
                    User updateUser = new User()
                            .setUsername(username)
                            .setEmailAddr(emailAddr)
                            .setPhone(user.getPhone())
                            .setRemark(user.getRemark())
                            .setId(userId);
                    updateUserList.add(updateUser);
                } else {
                    insertUserList.add(user);
                }

                deleteUserIdByUserOrg.add(userId);
                UserOrganization userOrganization = new UserOrganization()
                        .setOrganizationId(covertRootDeptId(deptId))
                        .setUserId(userId);
                userOrganizationList.add(userOrganization);
            }
        }

        if (CollectionUtils.isNotEmpty(updateUserList)) {
            userService.updateBatchById(updateUserList);
            LOGGER.info("sync update user finish, size: {}", updateUserList.size());
        }
        if (CollectionUtils.isNotEmpty(insertUserList)) {
            userService.saveBatch(insertUserList);
            LOGGER.info("sync insert user finish, size: {}", insertUserList.size());
        }
        if (CollectionUtils.isNotEmpty(deleteUserIdByUserOrg)) {
            userOrganizationService.deleteByUserIds(deleteUserIdByUserOrg);
            LOGGER.info("sync delete user-dept finish, size: {}", deleteUserIdByUserOrg.size());
        }
        if (CollectionUtils.isNotEmpty(userOrganizationList)) {
            userOrganizationService.saveBatch(userOrganizationList);
            LOGGER.info("sync insert user-dept finish, size: {}", userOrganizationList.size());
        }

        LOGGER.info("end sync organization and user to system, it's cost {}ms", System.currentTimeMillis() - startTimeMillis);

    }

}
