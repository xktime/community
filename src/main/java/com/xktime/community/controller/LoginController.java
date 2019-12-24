package com.xktime.community.controller;

import com.xktime.community.model.enums.ScopeEnum;
import com.xktime.community.service.login.GithubLoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    GithubLoginServiceImpl githubLoginService;

    @GetMapping("github")
    public String github() {
        String url = githubLoginService.getOAuthRedirectURL(UUID.randomUUID().toString(), ScopeEnum.USER, true);
        return "redirect:" + url;
    }
}
