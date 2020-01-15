package com.xktime.community.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Article {
    private int id;
    private String title;
    private String content;
    private String authorAccountId;
    private int viewCount;
    private Date postTime;

    public Article(String title, String content, String authorAccountId, Date postTime) {
        this.title = title;
        this.content = content;
        this.authorAccountId = authorAccountId;
        this.postTime = postTime;
    }
}
