package com.xktime.community.controller;

import com.xktime.community.model.dto.ArticleDTO;
import com.xktime.community.model.dto.CommentDTO;
import com.xktime.community.model.dto.PaginationDTO;
import com.xktime.community.model.entity.User;
import com.xktime.community.service.ArticleService;
import com.xktime.community.service.PaginationService;
import com.xktime.community.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        Object object = session.getAttribute("user");
        if (object instanceof User) {
            User user = (User) object;
            //帖子数据
            List<ArticleDTO> articleDTOList = paginationService.getArticleDTOList(page, user.getAccountId());
            model.addAttribute("articles", articleDTOList);
            //分页数据
            model.addAttribute("pagination", paginationService.getArticlePaginationDTO(page, user.getAccountId()));
        }
        return "my-articles";
    }

    @GetMapping("profile")
    public String profile(@RequestParam(name = "id") String accountId,
                          @RequestParam(name = "page", defaultValue = "1") int page,
                          Model model,
                          HttpServletRequest request) {
        User user = userService.findByAccountId(accountId);
        if (user != null) {
            Object object = request.getSession().getAttribute("user");
            //如果用户是自己，跳转到我的资料的页面
            if (object instanceof User && user.equals(object)) {
                model.addAttribute("title", "我");
            } else {
                model.addAttribute("title", "TA");
            }
            //给前端区分是评论还是帖子
            model.addAttribute("page", "article");
            model.addAttribute("user", userService.transferUserToUserDTO(user));
            //帖子数据
            List<ArticleDTO> articleDTOList = paginationService.getArticleDTOList(page, user.getAccountId());
            model.addAttribute("articles", articleDTOList);
            //帖子分页数据
            model.addAttribute("pagination", paginationService.getArticlePaginationDTO(page, user.getAccountId()));
        }
        return "profile";
    }

    @GetMapping("profile/comment")
    public String comment(@RequestParam(name = "id") String accountId,
                          @RequestParam(name = "page", defaultValue = "1") int page,
                          Model model,
                          HttpServletRequest request) {
        User user = userService.findByAccountId(accountId);
        if (user != null) {
            Object object = request.getSession().getAttribute("user");
            //如果用户是自己，跳转到我的资料的页面
            if (object instanceof User && user.equals(object)) {
                model.addAttribute("title", "我");
            } else {
                model.addAttribute("title", "TA");
            }
            //给前端区分是评论还是帖子
            model.addAttribute("page", "comment");
            model.addAttribute("user", userService.transferUserToUserDTO(user));
            //评论数据
            List<CommentDTO> commentDTOList = paginationService.getCommentDTOListByAccountId(page, user.getAccountId());
            model.addAttribute("comments", commentDTOList);
            //评论分页数据
            model.addAttribute("pagination", paginationService.getCommentPaginationDTOByAccountId(page, user.getAccountId()));
        }
        return "profile";
    }
}
