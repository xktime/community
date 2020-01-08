package com.xktime.community.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    private int articleId;
    private String authorAccountId;
    private String content;
    private Date postTime;
}
