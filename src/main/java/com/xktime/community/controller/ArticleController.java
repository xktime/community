package com.xktime.community.controller;

import com.xktime.community.model.entity.Article;
import com.xktime.community.model.entity.Comment;
import com.xktime.community.model.entity.User;
import com.xktime.community.service.ArticleService;
import com.xktime.community.service.CommentService;
import com.xktime.community.service.PaginationService;
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
@RequestMapping("article")
public class ArticleController {

    @Autowired
    ArticleService articleService;
    @Autowired
    CommentService commentService;
    @Autowired
    PaginationService paginationService;

    @GetMapping("/")
    public String article(@RequestParam(name = "page", defaultValue = "1") int page,
                          @RequestParam(name = "id") int articleId,
                          Model model) {
        Article article = articleService.findById(articleId);
        if (article != null) {
            //增加浏览量
            articleService.incView(articleId);
            //帖子的信息
            model.addAttribute("article", articleService.transferArticleToArticleDTO(article));
            //所有评论
            model.addAttribute("comments", paginationService.getCommentDTOListByArticleId(page, articleId));
            //评论分页
            model.addAttribute("pagination", paginationService.getCommentPaginationDTOByArticleId(page, articleId));
        }
        return "article";
    }

    @PostMapping("comment")
    public String post(@RequestParam(name = "id") int articleId,
                       @RequestParam(name = "content") String content,
                       HttpServletRequest request,
                       Model model) {
        model.addAttribute("content", content);
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "article";
        }
        Article article = articleService.findById(articleId);
        if (article == null) {
            model.addAttribute("error", "文章不存在");
            return "article";
        }
        if (StringUtils.isBlank(content)) {
            model.addAttribute("error", "内容不能为空");
            return "article";
        }
        //创建Comment对象,保存进数据库
        Comment comment = new Comment(articleId, user.getAccountId(), content, new Date());
        commentService.saveComment(comment);
        return "redirect:/article/?id=" + articleId;
    }
}
