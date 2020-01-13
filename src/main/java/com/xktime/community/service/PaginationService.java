package com.xktime.community.service;

import com.xktime.community.model.dto.ArticleDTO;
import com.xktime.community.model.dto.CommentDTO;
import com.xktime.community.model.dto.PaginationDTO;
import com.xktime.community.model.entity.Article;
import com.xktime.community.model.entity.Comment;
import com.xktime.community.repository.ArticleRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaginationService {

    private final static int PAGE_SHOW_NUM = 10;//每页显示多少条数据
    private final static int PAGE_BUTTON_NUM = 5;//显示页码按钮个数
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticleService articleService;

    @Autowired
    CommentService commentService;

    /**
     * 获取该页的所有帖子
     */
    public List<ArticleDTO> getArticleDTOList(int page) {
        return getArticleDTOList(page, null);
    }

    /**
     * 获取用户该页的所有帖子
     */
    public List<ArticleDTO> getArticleDTOList(int page, String accountId) {
        page = page < 1 ? 1 : page;
        //总页数
        int pageCount = accountId == null ? getArticlePageCount() : getArticlePageCount(accountId);
        pageCount = pageCount == 0 ? 1 : pageCount;
        int lastPage = pageCount;//最后一页为总页数
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
     * 获取该页的所有帖子
     */
    public List<CommentDTO> getCommentDTOListByArticleId(int page, int articleId) {
        page = page < 1 ? 1 : page;
        //总页数
        int pageCount = getCommentPageCountByArticleId(articleId);
        pageCount = pageCount == 0 ? 1 : pageCount;
        int lastPage = pageCount;//最后一页为总页数
        page = page > lastPage ? lastPage : page;
        int pageStartIndex = (page - 1) * PAGE_SHOW_NUM;//页面第一个帖子,在数据库的索引
        //获取当前页面所要显示的所有帖子
        List<Comment> commentList = commentService.findByArticleIdAndPage(pageStartIndex, PAGE_SHOW_NUM, articleId);
        return commentService.transferCommentListToCommentDTOList(commentList);
    }

    /**
     * 获取所有帖子该页的分页数据
     */
    public PaginationDTO getArticlePaginationDTO(int page) {
        int pageCount = getArticlePageCount();
        return getPaginationDTO(page, pageCount);
    }

    /**
     * 获取用户帖子该页的分页数据
     */
    public PaginationDTO getArticlePaginationDTO(int page, @NonNull String accountId) {
        int pageCount = getArticlePageCount(accountId);
        return getPaginationDTO(page, pageCount);
    }

    /**
     * 获取用户评论的分页数据
     */
    public PaginationDTO getCommentPaginationDTOByAccountId(int page, String accountId) {
        int pageCount = getCommentPageCountByAccountId(accountId);
        return getPaginationDTO(page, pageCount);
    }

    /**
     * 获取帖子评论的分页数据
     */
    public PaginationDTO getCommentPaginationDTOByArticleId(int page, int articleId) {
        int pageCount = getCommentPageCountByArticleId(articleId);
        return getPaginationDTO(page, pageCount);
    }

    /**
     * 获取分页数据
     *
     * @param page
     * @param pageCount
     * @return
     */
    private PaginationDTO getPaginationDTO(int page, int pageCount) {
        PaginationDTO pagination = new PaginationDTO();
        pagination.setPageNum(page);
        pageCount = pageCount == 0 ? 1 : pageCount;
        pagination.setPageCount(pageCount);
        //显示的第一个页码
        int firstPageNum = page - PAGE_BUTTON_NUM / 2;
        firstPageNum = firstPageNum < 1 ? 1 : firstPageNum;
        pagination.setFirstPageNum(firstPageNum);
        //显示的最后一个页码
        int lastPageNum = firstPageNum + PAGE_BUTTON_NUM - 1;
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
     * 获取帖子总页数
     */
    private int getArticlePageCount() {
        double articleCount = articleRepository.getCount();
        return (int) Math.ceil(articleCount / PAGE_SHOW_NUM);
    }

    /**
     * 获取用户帖子的总页数
     *
     * @param accountId
     * @return
     */
    private int getArticlePageCount(@NonNull String accountId) {
        double articleCount = articleRepository.getUsersArticleCount(accountId);
        return (int) Math.ceil(articleCount / PAGE_SHOW_NUM);
    }

    private int getCommentPageCountByAccountId(@NonNull String accountId) {
        double commentCount = commentService.getCommentsCountByAccountId(accountId);
        return (int) Math.ceil(commentCount / PAGE_SHOW_NUM);
    }

    private int getCommentPageCountByArticleId(@NonNull int articleId) {
        double commentCount = commentService.getCommentsCountByArticleId(articleId);
        return (int) Math.ceil(commentCount / PAGE_SHOW_NUM);
    }
}
