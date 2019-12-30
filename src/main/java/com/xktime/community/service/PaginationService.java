package com.xktime.community.service;

import com.xktime.community.model.dto.ArticleDTO;
import com.xktime.community.model.dto.PaginationDTO;
import com.xktime.community.model.entity.Article;
import com.xktime.community.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaginationService {

    private final static int PAGE_SHOW_NUM = 10;//每页显示多少条帖子
    private final static int PAGE_NUMBER_NUM = 5;//显示页码按钮个数
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticleService articleService;

    /**
     *  获取该页的所有帖子
     */
    public List<ArticleDTO> getArticleDTOListByPage(int page) {
        page = page < 1 ? 1 : page;
        int lastPage = getPageCount();
        page = page > lastPage ? lastPage : page;
        int pageTopIndex = (page - 1) * PAGE_SHOW_NUM;
        List<Article> articleList = articleRepository.getArticleListByPage(pageTopIndex, PAGE_SHOW_NUM);
        return articleService.transferArticleListToArticleDTOList(articleList);
    }

    /**
     *  获取该页的前端数据
     */
    public PaginationDTO getPaginationDTOByPage(int page) {
        PaginationDTO pagination = new PaginationDTO();
        pagination.setPageNum(page);
        int pageCount = getPageCount();
        pagination.setPageCount(pageCount);
        //显示的第一个页码
        int firstPageNum = page - PAGE_NUMBER_NUM / 2;
        firstPageNum = firstPageNum < 1 ? 1 : firstPageNum;
        pagination.setFirstPageNum(firstPageNum);
        //显示的最后一个页码
        int lastPageNum = firstPageNum + PAGE_NUMBER_NUM - 1;
        lastPageNum = lastPageNum > pageCount ? pageCount : lastPageNum;
        pagination.setLastPageNum(lastPageNum);
        if (page != 1) {
            //是否显示跳转上一页按钮
            pagination.setShowPreviousButton(true);
        }
        if (page != pageCount) {
            //是否显示跳转下一页按钮
            pagination.setShowNextButton(true);
        }
        if (firstPageNum != 1) {
            //是否显示跳转首页按钮
            pagination.setShowFirstButton(true);
        }
        if (lastPageNum != pageCount) {
            //是否显示跳转尾页按钮
            pagination.setShowLastButton(true);
        }
        return pagination;
    }

    /**
     * 获取总页数
     */
    public int getPageCount() {
        double articleCount = articleRepository.getCount();
        return (int) Math.ceil(articleCount / PAGE_SHOW_NUM);
    }
}
