package com.xktime.community.controller;

import com.xktime.community.model.dto.GithubUserDTO;
import com.xktime.community.service.GithubLoginService;
import com.xktime.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizeLoginController {

    @Autowired
    private GithubLoginService githubService;
    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) {
        GithubUserDTO user = githubService.getGithubUser(code, state);
        if (user != null) {
            userService.insert(user);
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }
}
