package com.xktime.community.repository;

import com.xktime.community.model.entity.Article;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleRepository {

    void saveArticle(Article article);

    Article findById(@Param("id") int id);

    List<Article> findAll();

    List<Article> findByOffset(@Param("pageStartIndex") int pageStartIndex,
                               @Param("pageShowNum") int pageShowNum);

    List<Article> findByOffsetAndAccountId(@Param("pageStartIndex") int pageStartIndex,
                                           @Param("pageShowNum") int pageShowNum,
                                           @Param("accountId") String accountId);

    List<Article> findByAccountId(@Param("accountId") String accountId);

    int getCount();

    int getCountByAccountId(@Param("accountId") String accountId);

    void incView(@Param("id") int id);
}
