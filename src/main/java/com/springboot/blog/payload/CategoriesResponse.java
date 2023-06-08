package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesResponse {
    private List<CategoryDto> content;
    private int pageNo;
    private int PageSize;
    private long totalElements;
    private  int totalPages;
    private boolean last;
}
