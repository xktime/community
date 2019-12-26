package com.xktime.community.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Article {
    private int id;
    private String title;
    private String content;
    private String authorAccountId;
    private int commentCount;
    private int viewCount;
    private Date postTime;

    public Article() {
    }

    public Article(String title, String content, String authorAccountId, Date postTime) {
        this.title = title;
        this.content = content;
        this.authorAccountId = authorAccountId;
        this.postTime = postTime;
    }
}
