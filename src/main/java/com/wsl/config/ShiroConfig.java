package com.wsl.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //第三部：创建ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("SecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){

        //先定义返回变量
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();

        //为返回变量设置相应属性，参考源码中的setting方法，安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        //添加shiro的内置过滤器
        /*
            anon:无需认证就能访问
            authc:必须认证才能访问
            user:必须拥有记住我功能才能访问
            perms:拥有某个资源的权限才能访问
            role:拥有某个角色权限才能访问
         */
        //拦截
        Map<String,String> filterMap =new LinkedHashMap<>();

        //授权
        filterMap.put("/user/add","perms[user:add]");//表示必须是user用户且有add权限，可以访问"/user/add"
        filterMap.put("/user/update","perms[user:update]");

//        filterMap.put("/user/add","authc");//支持通配符："/user/*"
//        filterMap.put("/user/update","authc");

        //设置登陆的请求
        bean.setLoginUrl("/toLogin");

        //设置未授权的请求
        bean.setUnauthorizedUrl("/noauth");

        bean.setFilterChainDefinitionMap(filterMap);

        return bean;

    }

    //第二步：创建DefaultWebSecurityManager
    @Bean(name = "SecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();

        //关联UserRealm
        securityManager.setRealm(userRealm);

        return securityManager;
    }


    //第一步：创建realm对象,需要自定义类
    @Bean(name = "userRealm")
    public UserRealm userRealm(){
        return new UserRealm();
    }

    //整合shiroDialect:用来整合shiro thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}

