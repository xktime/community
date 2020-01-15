package com.xktime.community.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDTO {
    private ArticleDTO article;
    private UserDTO author;
    private String content;
    private Date postTime;
}
