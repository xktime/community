package com.xktime.community.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager")DefaultWebSecurityManager webSecurityManager) {
        ShiroFilterFactoryBean filterBean = new ShiroFilterFactoryBean();
        filterBean.setSecurityManager(webSecurityManager);
        /**
         * anon:	无需认证即可访问
         * authc:	需要认证才可访问
         * user:	点击“记住我”功能可访问
         * perms:	拥有权限才可以访问 perms[权限名]
         * role:	拥有某个角色权限才能访问
         */
        //添加拦截
        Map<String, String> filterMap = new LinkedHashMap<>();//Map<url, 权限>
        filterMap.put("/publish/", "authc");
        filterMap.put("/login/*", "anon");
        filterBean.setFilterChainDefinitionMap(filterMap);
        return filterBean;
    }

    @Bean(name = "defaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    @Bean(name = "userRealm")
    public UserRealm getUserRealm() {
        return new UserRealm();
    }
}
