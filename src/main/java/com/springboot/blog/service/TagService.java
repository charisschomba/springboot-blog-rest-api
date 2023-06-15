package com.springboot.blog.service;

import com.springboot.blog.entity.Tag;
import com.springboot.blog.payload.TagDto;
import com.springboot.blog.payload.TagResponse;

public interface TagService {
    TagDto createTag(TagDto tagDto);
    TagResponse getTags(int pageNo, int pageSize, String sortBy, String sortDir);
    TagDto editTag(Long tagId, TagDto tagDto);
    String deleteTag(Long tagId);
}
