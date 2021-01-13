package com.qcz.qmplatform.config;

import cn.hutool.core.collection.CollectionUtil;
import com.qcz.qmplatform.common.constant.Constant;
import com.qcz.qmplatform.filter.LoginFilter;
import com.qcz.qmplatform.module.listen.ShiroSessionListener;
import com.qcz.qmplatform.module.system.realm.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
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

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public EhCacheManager ehCacheManager() {
        System.setProperty("net.sf.ehcache.skipUpdateCheck", "true");
        return new EhCacheManager();
    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdCookieEnabled(true);
        /**
         * @see com.qcz.qmplatform.common.utils.SubjectUtils.setUser
         */
//        sessionManager.setGlobalSessionTimeout(1800000L);
        sessionManager.setSessionIdCookie(simpleCookie());
        sessionManager.setCacheManager(ehCacheManager());
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
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(Constant.SUBJECT_ALGORITHM_NAME_MD5);
        credentialsMatcher.setHashIterations(Constant.SUBJECT_HASHTERATIONS);
        // 设置认证密码算法及迭代复杂度
        userRealm.setCredentialsMatcher(credentialsMatcher);
        userRealm.setCachingEnabled(true);
        userRealm.setCacheManager(ehCacheManager());
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
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/loginPage", "anon");
        filterChainDefinitionMap.put("/logout", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/**/noNeedLogin/**", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        Map<String, Filter> loginFilter = new HashMap<>(2);
        loginFilter.put("loginFilter", loginFilter());
        shiroFilterFactoryBean.setFilters(loginFilter);
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

    @Bean
    public LoginFilter loginFilter() {
        return new LoginFilter();
    }

}
