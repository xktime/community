package com.xktime.community.controller;

import com.xktime.community.model.dto.ArticleDTO;
import com.xktime.community.model.entity.User;
import com.xktime.community.service.CookieService;
import com.xktime.community.service.ArticleService;
import com.xktime.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;
    @Autowired
    private CookieService cookieService;
    @Autowired
    private ArticleService articleService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") int page) {
        String token = cookieService.getToken(request.getCookies());
        if (token != null) {
            User user = userService.findByToken(token);
            if (user != null) {
                request.getSession().setAttribute("user", user);
            }
        }
        List<ArticleDTO> articleDTOList = articleService.getArticleDTOList();
        model.addAttribute("articles", articleDTOList);
        return "index";
    }

}
