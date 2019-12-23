package com.xktime.community.controller;

import com.xktime.community.model.entity.User;
import com.xktime.community.service.CookieService;
import com.xktime.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;
    @Autowired
    private CookieService cookieService;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        String token = cookieService.getToken(request.getCookies());
        if (token != null) {
            User user = userService.findByToken(token);
            if (user != null) {
                request.getSession().setAttribute("user", user);
            }
        }
        return "index";
    }

}
