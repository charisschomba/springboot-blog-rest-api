package com.springboot.blog.payload;

import lombok.Data;

import java.util.List;

@Data
public class CommentResponse {
    private List<CommentDto> content;
    private int pageNo;
    private int PageSize;
    private long totalElements;
    private  int totalPages;
    private boolean last;
}
