package com.xktime.community.controller;

import com.xktime.community.model.dto.ArticleDTO;
import com.xktime.community.model.entity.Article;
import com.xktime.community.model.entity.User;
import com.xktime.community.service.ArticleService;
import com.xktime.community.service.PaginationService;
import com.xktime.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("account")
public class AccountController {

    @Autowired
    public PaginationService paginationService;
    @Autowired
    public UserService userService;
    @Autowired
    public ArticleService articleService;

    @GetMapping("article")
    public String myArticle(HttpServletRequest request,
                            Model model,
                            @RequestParam(name = "page", defaultValue = "1") int page) {
        model.addAttribute("title", "我的帖子");
        Object object = request.getSession().getAttribute("user");
        if (object instanceof User) {
            User user = (User) object;
            //帖子数据
            List<ArticleDTO> articleDTOList = paginationService.getArticleDTOListByPage(page, user.getAccountId());
            model.addAttribute("articles", articleDTOList);
            //分页数据
            model.addAttribute("pagination", paginationService.getPaginationDTOByPage(page, user.getAccountId()));
        }
        return "my-article";
    }

    @GetMapping("profile")
    public String profile(@RequestParam(name = "id") String accountId,
                          Model model,
                          @RequestParam(name = "page", defaultValue = "1") int page) {
        User user = userService.findByAccountId(accountId);
        if (user != null) {
            model.addAttribute("user", userService.transferUserToUserDTO(user));
            model.addAttribute("title", "TA的帖子");
            //帖子数据
            List<ArticleDTO> articleDTOList = paginationService.getArticleDTOListByPage(page, user.getAccountId());
            model.addAttribute("articles", articleDTOList);
            //分页数据
            model.addAttribute("pagination", paginationService.getPaginationDTOByPage(page, user.getAccountId()));
        }
        return "profile";
    }
}
