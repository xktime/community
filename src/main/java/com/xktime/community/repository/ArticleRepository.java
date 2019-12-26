package com.xktime.community.repository;

import com.xktime.community.model.entity.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleRepository {

    @Insert("INSERT INTO article(title,content,author_account_id,post_time) " +
            "VALUES (#{title},#{content},#{authorId},#{postTime})")
    void saveArticle(Article article);

    @Select("SELECT * FROM article")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "authorId", column = "author_account_id"),
            @Result(property = "postTime", column = "post_time"),
            @Result(property = "viewCount", column = "view_count"),
            @Result(property = "commentCount", column = "comment_count"),
    })
    List<Article> getArticles();
}
