package com.xktime.community.controller;

import com.xktime.community.model.entity.User;
import com.xktime.community.service.login.GithubLoginServiceImpl;
import com.xktime.community.service.UserService;
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

    @GetMapping("/github")
    public String github(@RequestParam(name = "code") String code,
                         @RequestParam(name = "state") String state,
                         HttpServletResponse response) {
        User user = githubService.getUser(code, state);
        if (user != null) {
            userService.saveUser(user);
            Cookie cookie = new Cookie("token", user.getToken());
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }
}
