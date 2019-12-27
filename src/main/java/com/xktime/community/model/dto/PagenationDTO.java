package com.xktime.community.model.dto;

import lombok.Data;

@Data
public class PagenationDTO {
    private int page;//当前页码
    private int pageCount;//总页数
}
