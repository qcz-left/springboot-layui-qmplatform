package com.qcz.qmplatform;

import com.qcz.qmplatform.common.filter.LoginFilter;
import com.qcz.qmplatform.common.redis.ShiroRedisCacheManager;
import com.qcz.qmplatform.common.utils.Constants;
import com.qcz.qmplatform.module.sys.realm.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
@Configuration
public class ShiroConfigurer {
    @Bean
    public ShiroRedisCacheManager shiroRedisCacheManager(RedisTemplate redisTemplate) {
        ShiroRedisCacheManager redisCacheManager = new ShiroRedisCacheManager(redisTemplate);
        // name是key的前缀，可以设置任何值，无影响，可以设置带项目特色的值
        redisCacheManager.createCache(Constants.SHIRO_REDIS_NAME);
        return redisCacheManager;
    }

    @Bean
    public UserRealm userRealm(RedisTemplate redisTemplate) {
        UserRealm userRealm = new UserRealm();
        // 设置缓存管理器
        userRealm.setCacheManager(shiroRedisCacheManager(redisTemplate));

        // 加密设置
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(Constants.SUBJECT_ALGORITHM_NAME_MD5);
        credentialsMatcher.setHashIterations(Constants.SUBJECT_HASHTERATIONS);
        // 设置认证密码算法及迭代复杂度
        userRealm.setCredentialsMatcher(credentialsMatcher);
        userRealm.setCachingEnabled(true);
        // 认证
        userRealm.setAuthenticationCachingEnabled(false);
        // 授权
        userRealm.setAuthorizationCachingEnabled(false);
        return userRealm;
    }

    @Bean
    SecurityManager securityManager(RedisTemplate redisTemplate) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(userRealm(redisTemplate));
        manager.setCacheManager(shiroRedisCacheManager(redisTemplate));
        return manager;
    }

    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // LoginFilter 过滤器中已经重写了重定向的方法，这里不需要在设置登录页面
        //shiroFilterFactoryBean.setLoginUrl("/main/loginPage");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/main/login", "anon");
        filterChainDefinitionMap.put("/main/loginPage", "anon");
        filterChainDefinitionMap.put("/main/logout", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/**/exportData", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        Map<String, Filter> loginFilter = new HashMap<>();
        loginFilter.put("loginFilter", loginFilter());
        shiroFilterFactoryBean.setFilters(loginFilter);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean("loginFilter")
    public LoginFilter loginFilter() {
        return new LoginFilter();
    }
}
