package com.xktime.community.model.dto;

import com.xktime.community.model.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class ArticleDTO {
    private String title;
    private String content;
    private User author;
    private int commentCount;
    private int viewCount;
    private Date postTime;
}
