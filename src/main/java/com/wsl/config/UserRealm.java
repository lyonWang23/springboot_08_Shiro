package com.wsl.config;

import com.wsl.pojo.User;
import com.wsl.service.UserService;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权");

        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();

        //info.addStringPermission("user:add");
        //拿到当前用户登陆对象
        Subject subject= SecurityUtils.getSubject();

        //如果认证成功，通过subject.getPrincipal()可以获取数据库存的实际用户对象
        User currentUser= (User) subject.getPrincipal();//拿到User对象

        //根据对象的属性值设置当前用户对象的权限值
        info.addStringPermission(currentUser.getPerms());//根据对象的属性值设置当前用户对象的权限值

        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了认证");

        //用户名，密码，数据库中获取
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;

        User user = userService.queryUserByName(userToken.getUsername());//获取用户名


        if(user==null){//说明查无此人
            return null;//对应subject.login(token)将会抛出异常UnknownAccountException
        }

        String name=user.getName();
        String password=user.getPwd();

        //密码认证,SimpleAuthenticationInfo(实际从数据库获取的用户对象，实际对象的密码信息，realmName)
        return new SimpleAuthenticationInfo(user,password,"");//放入User对象

    }


}

