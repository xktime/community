package com.xktime.community.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Article {
    private int id;
    private String title;
    private String content;
    private String authorId;
    private int commentCount;
    private int viewCount;
    private Date postTime;

    public Article() {
    }

    public Article(String title, String content, String authorId, Date postTime) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.postTime = postTime;
    }
}
