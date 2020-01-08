package com.xktime.community.model.dto;

import com.xktime.community.model.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class ArticleDTO {
    private int id;
    private String title;
    private String content;
    private UserDTO author;
    private int commentCount;
    private int viewCount;
    private Date postTime;
}
