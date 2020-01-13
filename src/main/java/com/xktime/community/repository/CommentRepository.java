package com.xktime.community.repository;

import com.xktime.community.model.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface CommentRepository {

    @Insert("INSERT INTO comment(article_id,author_account_id,post_time,content) " +
            "VALUES (#{articleId},#{authorAccountId},#{postTime},#{content}) ")
    void saveComment(Comment comment);

    @Select("SELECT * FROM comment WHERE article_id = #{articleId}")
    List<Comment> findByArticleId(@Param("articleId") int articleId);

    @Select("SELECT * FROM comment WHERE author_account_id = #{accountId}")
    List<Comment> findByAccountId(@Param("accountId") String accountId);

    @Select("SELECT COUNT(*) FROM comment WHERE article_id = #{articleId}")
    int getCommentCountByArticleId(@Param("articleId") int articleId);

    @Select("SELECT COUNT(*) FROM comment WHERE author_account_id = #{accountId}")
    int getCommentCountByAccountId(@Param("accountId") String accountId);
}
