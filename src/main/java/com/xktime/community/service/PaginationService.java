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
     * 获取该页的所有帖子
     */
    public List<ArticleDTO> getArticleDTOListByPage(int page) {
        return getArticleDTOListByPage(page, null);
    }

    /**
     * 获取用户该页的所有帖子
     */
    public List<ArticleDTO> getArticleDTOListByPage(int page, String accountId) {
        page = page < 1 ? 1 : page;
        int lastPage = getPageCount(accountId);
        page = page > lastPage ? lastPage : page;
        int pageStartIndex = (page - 1) * PAGE_SHOW_NUM;//页面第一个帖子,在数据库的索引
        //获取当前页面所要显示的所有帖子
        List<Article> articleList;
        if (accountId != null) {
            //如果accountId为空返回该页所有帖子，否则返回用户的该页帖子
            articleList = articleRepository.getUsersArticleListByPage(pageStartIndex, PAGE_SHOW_NUM, accountId);
        } else {
           articleList = articleRepository.getArticleListByPage(pageStartIndex, PAGE_SHOW_NUM);
        }
        return articleService.transferArticleListToArticleDTOList(articleList);
    }

    /**
     * 获取该页的分页数据
     */
    public PaginationDTO getPaginationDTOByPage(int page) {
        return getPaginationDTOByPage(page, null);
    }

    /**
     *  获取用户帖子的分页数据
     * @param page
     * @param accountId 如果accountId==0 表示所有的帖子。
     * @return
     */
    public PaginationDTO getPaginationDTOByPage(int page, String accountId) {
        PaginationDTO pagination = new PaginationDTO();
        pagination.setPageNum(page);
        int pageCount = getPageCount(accountId);
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
        return getPageCount(null);
    }

    /**
     * 如果accountId为空获取所有帖子的总页数，否则获取用户帖子的总页数
     * @param accountId
     * @return
     */
    public int getPageCount(String accountId) {
        double articleCount;
        if (accountId == null) {
            articleCount = articleRepository.getCount();
        } else {
            articleCount = articleRepository.getUsersArticleCount(accountId);
        }
        return (int) Math.ceil(articleCount / PAGE_SHOW_NUM);
    }
}
