package com.xktime.community.repository;

import com.xktime.community.model.entity.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleRepository {

    @Insert("INSERT INTO article(title,content,author_account_id,post_time) " +
            "VALUES (#{title},#{content},#{authorAccountId},#{postTime})")
    void saveArticle(Article article);

    @Select("SELECT * FROM article")
    List<Article> getArticles();
}
