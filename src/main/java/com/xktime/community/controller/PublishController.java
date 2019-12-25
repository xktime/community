package com.xktime.community.controller;

import com.xktime.community.model.entity.Article;
import com.xktime.community.model.entity.User;
import com.xktime.community.service.PublishService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("publish")
public class PublishController {

    @Autowired
    PublishService publishService;
    @GetMapping("/")
    public String publish() {
        return "publish";
    }

    @PostMapping("post")
    public String post(@RequestParam(value = "title", required = false) String title,
                       @RequestParam(value = "content", required = false) String content,
                       HttpServletRequest request,
                       Model model) {
        model.addAttribute("title", title);
        model.addAttribute("content", content);
        if (StringUtils.isBlank(title)) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(content)) {
            model.addAttribute("error", "内容不能为空");
            return "publish";
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        Article article = new Article(title, content, user.getAccountId(), new Date());
        publishService.saveArticle(article);
        return "redirect:/";
    }
}
