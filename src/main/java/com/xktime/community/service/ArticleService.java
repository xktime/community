package com.xktime.community.service;

import com.xktime.community.model.dto.ArticleDTO;
import com.xktime.community.model.entity.Article;
import com.xktime.community.model.entity.User;
import com.xktime.community.repository.ArticleRepository;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    public void saveArticle(Article article) {
        articleRepository.saveArticle(article);
    }

    public List<Article> getArticleList() {
        return articleRepository.getArticles();
    }

    public List<Article> getArticleListByAccountId(String accountId) {
        return articleRepository.getArticlesByAccountId(accountId);
    }

    public void incView(int articleId) {

    }

    public Article getArticleById(int id) {
        return articleRepository.getArticleById(id);
    }

    /**
     *  把后端数据转换成前端显示数据类型
     */
    public ArticleDTO transferArticleToArticleDTO(@NonNull Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        User author = userService.findByAccountId(article.getAuthorAccountId());
        if (author == null) {
            //获得注销用户信息来显示
            author = userService.getCanceledUser();
        }
        articleDTO.setAuthor(userService.transferUserToUserDTO(author));
        BeanUtils.copyProperties(article, articleDTO);
        articleDTO.setCommentCount(commentService.getCommentCount(article.getId()));
        if (articleDTO.getPostTime() == null) {
            //如果日期为空,默认时间为1970年
            long time = 0L;
            articleDTO.setPostTime(new Date(time));
        }
        return articleDTO;
    }

    /**
     *  把后端数据转换成前端显示数据类型
     */
    public List<ArticleDTO> transferArticleListToArticleDTOList(List<Article> articles) {
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        if (articles != null && !articles.isEmpty()) {
            for (Article article : articles) {
                articleDTOList.add(transferArticleToArticleDTO(article));
            }
        }
        return articleDTOList;
    }
}
