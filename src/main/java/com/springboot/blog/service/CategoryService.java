package com.springboot.blog.service;

import com.springboot.blog.payload.CategoriesResponse;
import com.springboot.blog.payload.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto category);
    CategoryDto getCategory(Long id);
    CategoriesResponse getAllCategories(int pageNo, int pageSize, String sortBy, String sortDir);
    CategoryDto updateCategories(Long categoryId, CategoryDto categoryDto);
    String deleteCategory(Long id);
}
