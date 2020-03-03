package com.xktime.community.repository;

import com.xktime.community.model.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Repository
public interface CommentRepository {

    void saveComment(Comment comment);

    List<Comment> findByArticleId(@Param("articleId") int articleId);

    List<Comment> findByAccountId(@Param("accountId") String accountId);

    List<Comment> findByOffsetAndArticleId(@Param("pageStartIndex") int pageStartIndex,
                                           @Param("pageShowNum") int pageShowNum,
                                           @Param("articleId") int articleId);

    List<Comment> findByOffsetAndAccountId(@Param("pageStartIndex") int pageStartIndex,
                                           @Param("pageShowNum") int pageShowNum,
                                           @Param("accountId") String accountId);

    int getCommentCountByArticleId(@Param("articleId") int articleId);

    int getCommentCountByAccountId(@Param("accountId") String accountId);
}
