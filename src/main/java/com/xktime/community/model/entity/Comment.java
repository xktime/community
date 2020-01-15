package com.xktime.community.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Comment {
    private int id;
    private int articleId;
    private String authorAccountId;
    private String content;
    private Date postTime;

    public Comment(int articleId, String authorAccountId, String content, Date postTime) {
        this.articleId = articleId;
        this.authorAccountId = authorAccountId;
        this.content = content;
        this.postTime = postTime;
    }
}
