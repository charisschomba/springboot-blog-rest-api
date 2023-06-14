package com.springboot.blog.service;

import com.springboot.blog.payload.TagDto;

public interface TagService {
    TagDto createTag(TagDto tagDto);
}
