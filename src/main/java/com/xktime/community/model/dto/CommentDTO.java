package com.xktime.community.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDTO {
    private int articleId;
    private UserDTO author;
    private String content;
    private Date postTime;
}
