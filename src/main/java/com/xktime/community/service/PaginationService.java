package com.xktime.community.service;

import com.xktime.community.model.dto.ArticleDTO;
import com.xktime.community.model.entity.Article;
import com.xktime.community.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaginationService {

    private final static int PAGE_SHOW_NUM = 2;//每页显示多少条帖子

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticleService articleService;

    public List<ArticleDTO> getArticleDTOListByPage(int page) {
        page = page < 1 ? 1 : page;
        int lastPage = getPageCount();
        page = page > lastPage ? lastPage : page;
        int pageTopIndex = (page - 1) * PAGE_SHOW_NUM;
        List<Article> articleList = articleRepository.getArticlesByPage(pageTopIndex, PAGE_SHOW_NUM);
        return articleService.transferArticleListToArticleDTOList(articleList);
    }

    public int getPageCount() {
        double articleCount = articleRepository.getCount();
        return (int) Math.ceil(articleCount / PAGE_SHOW_NUM);
    }
}
