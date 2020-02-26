package com.xktime.community.controller;

import com.xktime.community.model.entity.User;
import com.xktime.community.service.UserService;
import com.xktime.community.service.login.GithubLoginServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("callback")
public class AuthorizeCallbackController {

    @Autowired
    private GithubLoginServiceImpl githubService;
    @Autowired
    private UserService userService;

    @GetMapping("github")
    public String github(@RequestParam(name = "code") String code,
                         @RequestParam(name = "state") String state,
                         HttpServletResponse response) {
        //根据传入的code和state从获取github的用户数据
        User user = githubService.getUser(code, state);
        if (user != null && user.getAccountId() != null) {
            //如果取到用户数据，保存用户数据，并更新token
            userService.saveUser(user);
            Cookie cookie = new Cookie("token", user.getToken());
            cookie.setPath("/");
            response.addCookie(cookie);
            //Shiro验证登陆
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getAccountId(), user.getToken());
            subject.login(usernamePasswordToken);
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }
}
