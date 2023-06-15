package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class TagResponse {
    private List<TagDto> content;
    private int pageNo;
    private int PageSize;
    private long totalElements;
    private  int totalPages;
    private boolean last;
}
