package com.xktime.community.model.entity;

import java.util.Date;

public class Article {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }
}
