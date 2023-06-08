package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CategoriesResponse;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImp(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public CategoryDto addCategory(CategoryDto categoryDto)  {

        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);

        return  modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoriesResponse getAllCategories(int pageNo, int pageSize, String sortBy, String sortDir) {
        // create Sort instance
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Category> categories = categoryRepository.findAll(pageable);

        CategoriesResponse response = categoriesResponse(categories);
       return response;
    }

    @Override
    public CategoryDto updateCategories(Long categoryId, CategoryDto categoryDto) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        Category updatedCategory = categoryRepository.save(category);

        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        categoryRepository.delete(category);
        return "Category delete successfully";
    }

    private CategoriesResponse categoriesResponse(Page<Category> categories) {

        List<CategoryDto> listOfCategories = categories.getContent()
                .stream().map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());

        CategoriesResponse categoriesResponse = new CategoriesResponse();

        categoriesResponse.setContent(listOfCategories);
        categoriesResponse.setPageNo(categories.getNumber());
        categoriesResponse.setPageSize(categories.getSize());
        categoriesResponse.setLast(categories.isLast());
        categoriesResponse.setTotalPages(categories.getTotalPages());
        categoriesResponse.setTotalElements(categories.getTotalElements());

        return categoriesResponse;
    }
}
