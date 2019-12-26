package com.xktime.community.service;

import com.xktime.community.model.dto.ArticleDTO;
import com.xktime.community.model.entity.Article;
import com.xktime.community.model.entity.User;
import com.xktime.community.repository.ArticleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublishService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserService userService;

    public void saveArticle(Article article){
        articleRepository.saveArticle(article);
    }

    public List<Article> getArticleList(){
        return articleRepository.getArticles();
    }

    public List<ArticleDTO> getArticleDTOList() {
        List<Article> articleList = getArticleList();
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        if (articleList != null && !articleList.isEmpty()) {
            for (Article article : articleList) {
                articleDTOList.add(transferArticleToArticleDTO(article));
            }
        }
        return articleDTOList;
    }

    private ArticleDTO transferArticleToArticleDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        User author = userService.findByAccountId(article.getAuthorAccountId());
        if (author == null) {
            throw new NullPointerException("未找到User");
        }
        articleDTO.setAuthor(author);
        BeanUtils.copyProperties(article, articleDTO);
        return  articleDTO;
    }
}
