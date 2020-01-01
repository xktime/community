package com.xktime.community.controller;

import com.xktime.community.model.dto.ArticleDTO;
import com.xktime.community.model.entity.Article;
import com.xktime.community.model.entity.User;
import com.xktime.community.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("account")
public class AccountController {

    @Autowired
    public ArticleService articleService;

    @GetMapping("article")
    public String myArticle(HttpServletRequest request,
                            Model model) {
        model.addAttribute("title", "我的帖子");
        Object object = request.getSession().getAttribute("user");
        if (object instanceof User) {
            User user = (User)object;
            List<Article> articleList = articleService.getArticleListByAccountId(user.getAccountId());
            //帖子数据
            List<ArticleDTO> articleDTOList = articleService.transferArticleListToArticleDTOList(articleList);
            model.addAttribute("articles", articleDTOList);
        }
        return "index";
    }
}
