package com.wsl.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class MyController {

    @RequestMapping({"/","/index"})
    public String toIndex(Model model){
        model.addAttribute("msg","hello,shiro");
        return "index";
    }

    @RequestMapping("/user/add")
    public String add(){
        return "user/add";
    }

    @RequestMapping("/user/update")
    public String update(){
        return "user/update";
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(String username, String password, Model model, HttpSession session){

        //第一步：获取当前用户
        Subject subject= SecurityUtils.getSubject();

        //第二步：封装用户的登陆数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        System.out.println(username + " : "+password);

        //第三步：使用当前用户subject执行login(token)方法，根据realm的认证方法的返回值，显示认证结果到前端
        try{
            subject.login(token);//执行登陆的方法，如果没有异常就ok

            //执行到此处说明已经login成功，设置一个参数到session中，
            // 方便前端根据该参数决定是否显示登陆按钮
            session.setAttribute("loginUser",username);


            return "index";
        }catch (UnknownAccountException e){//用户名不存在
            model.addAttribute("msg","用户名错误");
            return "login";
        }catch (IncorrectCredentialsException e){//密码不存在
            model.addAttribute("msg","密码错误");
            return "login";
        }

    }

    @RequestMapping("/noauth")
    @ResponseBody
    public String unauthorized(){
        return "未经授权禁止访问";

    }

}

