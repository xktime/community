package com.xktime.community.model.dto;

import lombok.Data;

@Data
public class PaginationDTO {
    private int pageNum;//当前页码
    private int pageCount;//总页数
    private boolean showFirstButton;
    private boolean showLastButton;
    private boolean showPreviousButton;
    private boolean showNextButton;
    private int firstPageNum;//显示的第一个页码数
    private int lastPageNum;//显示的最后一个页码数
}
