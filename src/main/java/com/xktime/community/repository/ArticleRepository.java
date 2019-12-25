package com.xktime.community.repository;

import com.xktime.community.model.entity.Article;
import com.xktime.community.model.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleRepository {

    @Insert("INSERT INTO article(title,content,author_account_id,post_time) VALUES (#{title},#{content},#{authorId},#{postTime})")
    void saveArticle(Article article);
}
