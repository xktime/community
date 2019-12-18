package com.xktime.community.controller;

import com.xktime.community.dto.AccessTokenDTO;
import com.xktime.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setState(state);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id("34d536913cf2d29794e9");
        accessTokenDTO.setRedirect_uri("http://localhost:8888/callback");
        accessTokenDTO.setClient_secret("912aa300c735825852862678f222380cf079f974");
        githubProvider.getAccessToken(accessTokenDTO);
        return "index";
    }
}
