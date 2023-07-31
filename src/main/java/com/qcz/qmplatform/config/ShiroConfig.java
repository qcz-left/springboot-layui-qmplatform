package com.qcz.qmplatform.config;

import cn.hutool.core.collection.CollectionUtil;
import com.qcz.qmplatform.common.constant.Constant;
import com.qcz.qmplatform.common.cache.ShiroCacheManager;
import com.qcz.qmplatform.module.listen.ShiroSessionListener;
import com.qcz.qmplatform.module.system.realm.CustomCredentialsMatch;
import com.qcz.qmplatform.module.system.realm.UserRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroCacheManager shiroCacheManager() {
        return new ShiroCacheManager();
    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setGlobalSessionTimeout(1800000L);
        sessionManager.setSessionIdCookie(simpleCookie());
        sessionManager.setCacheManager(shiroCacheManager());
        sessionManager.setSessionListeners(CollectionUtil.newArrayList(sessionListener()));
        sessionManager.setSessionValidationInterval(5 * 60 * 1000L);
        return sessionManager;
    }

    @Bean
    public ShiroSessionListener sessionListener() {
        return new ShiroSessionListener();
    }

    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    @Bean
    public SimpleCookie simpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName("shiro.sesssion");
        return simpleCookie;
    }

    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        // 加密设置
        CustomCredentialsMatch credentialsMatcher = new CustomCredentialsMatch();
        credentialsMatcher.setHashAlgorithmName(Constant.SUBJECT_ALGORITHM_NAME_MD5);
        credentialsMatcher.setHashIterations(Constant.SUBJECT_HASHTERATIONS);
        // 设置认证密码算法及迭代复杂度
        userRealm.setCredentialsMatcher(credentialsMatcher);
        userRealm.setCachingEnabled(true);
        userRealm.setCacheManager(shiroCacheManager());
        // 认证
        userRealm.setAuthenticationCachingEnabled(false);
        // 授权
        userRealm.setAuthorizationCachingEnabled(false);
        return userRealm;
    }

    @Bean
    SecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(userRealm());
        manager.setSessionManager(sessionManager());
        return manager;
    }

    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setSuccessUrl("/");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setLoginUrl("/loginPage");
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/loginPage", "anon");
        filterChainDefinitionMap.put("/logout", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/**/noNeedLogin/**", "anon");
        filterChainDefinitionMap.put("/operation/loginSetting/get", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
