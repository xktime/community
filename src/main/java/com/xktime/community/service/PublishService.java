package com.xktime.community.service;

import com.xktime.community.model.entity.Article;
import com.xktime.community.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublishService {

    @Autowired
    ArticleRepository articleRepository;
    public void saveArticle(Article article){
        articleRepository.saveArticle(article);
    }
}
