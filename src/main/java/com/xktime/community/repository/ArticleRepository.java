package com.xktime.community.repository;

import com.xktime.community.model.entity.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleRepository {

    @Insert("INSERT INTO article(title,content,author_account_id,post_time) " +
            "VALUES (#{title},#{content},#{authorAccountId},#{postTime})")
    void saveArticle(Article article);

    @Select("SELECT COUNT(*) FROM article")
    int getCount();

    @Select("SELECT COUNT(*) FROM article WHERE author_account_id = #{accountId}")
    int getUsersArticleCount(@Param("accountId") String accountId);

    @Select("SELECT * FROM article ORDER BY post_time DESC")
    List<Article> getArticles();

    @Select("SELECT * FROM article " +
            "ORDER BY post_time DESC " +
            "LIMIT #{pageShowNum} OFFSET #{pageStartIndex}")
    List<Article> getArticleListByPage(@Param("pageStartIndex") int pageStartIndex,
                                       @Param("pageShowNum") int pageShowNum);

    @Select("SELECT * FROM article WHERE author_account_id = #{accountId} " +
            "ORDER BY post_time DESC " +
            "LIMIT #{pageShowNum} OFFSET #{pageStartIndex}")
    List<Article> getUsersArticleListByPage(@Param("pageStartIndex") int pageStartIndex,
                                            @Param("pageShowNum") int pageShowNum,
                                            @Param("accountId") String accountId);

    @Select("SELECT * FROM article WHERE author_account_id = #{accountId} ORDER BY post_time DESC")
    List<Article> getArticlesByAccountId(@Param("accountId") String accountId);
}
