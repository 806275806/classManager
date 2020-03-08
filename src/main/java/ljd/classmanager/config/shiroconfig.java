package ljd.classmanager.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import ljd.classmanager.realm.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: springboot-shiro-demo
 * @description: shiro配置类
 * @author: liu yan
 * @create: 2019-11-15 20:02
 */
@Configuration
public class shiroconfig {
    @Bean
    public ShiroFilterFactoryBean getshiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String,String> filterMap=new LinkedHashMap<String, String>();
        filterMap.put("/login","anon");
        filterMap.put("/wechat/**","anon");
        filterMap.put("/statics/**","anon");
        filterMap.put("/templates/**","anon");
        filterMap.put("/getCode","anon");
        filterMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setLoginUrl("/tologin");
        return shiroFilterFactoryBean;
    }
//    创建DefaultWebSecurityManager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
//        关联realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }
//创建Realm
    @Bean(name = "userRealm")
    public UserRealm userRealm(@Qualifier("hashedCredentialsMatcher")HashedCredentialsMatcher hashedCredentialsMatcher){
        UserRealm userRealm=new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return userRealm;
    }
    //配置shiroDialect，用于thymleaf和shiro标签整合使用
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
    //MD5加密
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //指定加密方式为MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //加密次数
        credentialsMatcher.setHashIterations(1);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }
}