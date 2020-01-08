package com.xktime.community.controller;

import com.xktime.community.model.entity.Article;
import com.xktime.community.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @GetMapping("/")
    public String article(@RequestParam(name = "id", defaultValue = "1") int articleId,
                          Model model) {
        Article article = articleService.getArticleById(articleId);
        model.addAttribute("article", articleService.transferArticleToArticleDTO(article));
        return "article";
    }
}
