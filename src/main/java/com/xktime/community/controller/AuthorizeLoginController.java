package com.xktime.community.controller;

import com.xktime.community.model.dto.GithubUserDTO;
import com.xktime.community.model.entity.User;
import com.xktime.community.service.GithubLoginService;
import com.xktime.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthorizeLoginController {

    @Autowired
    private GithubLoginService githubService;
    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        GithubUserDTO githubUser = githubService.getGithubUser(code, state);
        if (githubUser != null) {
            User user = userService.transferGithubUserToUser(githubUser);
            if (user != null) {
                userService.saveUser(user);
                request.getSession().setAttribute("user", user);
                response.addCookie(new Cookie("token", user.getToken()));
            }
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }
}
