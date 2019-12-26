package com.xktime.community.controller;

import com.xktime.community.model.dto.ArticleDTO;
import com.xktime.community.model.entity.User;
import com.xktime.community.service.CookieService;
import com.xktime.community.service.PublishService;
import com.xktime.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;
    @Autowired
    private CookieService cookieService;
    @Autowired
    private PublishService publishService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {
        String token = cookieService.getToken(request.getCookies());
        if (token != null) {
            User user = userService.findByToken(token);
            if (user != null) {
                request.getSession().setAttribute("user", user);
            }
        }
        List<ArticleDTO> articleDTOList = publishService.getArticleDTOList();
        model.addAttribute("articles", articleDTOList);
        return "index";
    }

}
