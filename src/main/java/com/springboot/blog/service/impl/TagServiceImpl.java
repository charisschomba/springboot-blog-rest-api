package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Tag;
import com.springboot.blog.payload.TagDto;
import com.springboot.blog.repository.TagRepository;
import com.springboot.blog.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private ModelMapper mapper;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public TagDto createTag(TagDto tagDto) {
        Tag tag = mapper.map(tagDto, Tag.class);
        Tag newTagEntity = tagRepository.save(tag);
        return mapper.map(tag, TagDto.class);
    }
}