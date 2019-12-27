package com.xktime.community.model.dto;

import lombok.Data;

@Data
public class PaginationDTO {
    private int page;//当前页码
    private int pageCount;//总页数
    private boolean showFirstButton;
    private boolean showLastButton;
}
