package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Tag;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.*;
import com.springboot.blog.repository.TagRepository;
import com.springboot.blog.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final ModelMapper mapper;

    public TagServiceImpl(TagRepository tagRepository, ModelMapper mapper
    ) {
        this.tagRepository = tagRepository;
        this.mapper = mapper;
    }

    @Override
    public TagDto createTag(TagDto tagDto) {
        Tag tag = mapper.map(tagDto, Tag.class);
        Tag newTagEntity = tagRepository.save(tag);
        return mapper.map(newTagEntity, TagDto.class);
    }

    @Override
    public TagResponse getTags(int pageNo, int pageSize, String sortBy, String sortDir) {
        // create Sort instance
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Tag> tags = tagRepository.findAll(pageable);

       return formatResponse(tags);
    }

    @Override
    public TagDto editTag(Long tagId, TagDto tagDto) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag", "Id", tagId));
        tag.setName(tagDto.getName());
        tagRepository.save(tag);

        return mapper.map(tag, TagDto.class);
    }

    @Override
    public String deleteTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag", "Id", tagId));
        tagRepository.delete(tag);
        return "Tag deleted successfully";
    }

    private TagResponse formatResponse(Page<Tag> tags) {
        //get content for page object
        List<Tag> listOfTags = tags.getContent();

        List<TagDto> content =  listOfTags.stream()
                .map(post -> mapper.map(post, TagDto.class))
                .collect(Collectors.toList());
        TagResponse tagResponse= new TagResponse();
        tagResponse.setContent(content);
        tagResponse.setPageNo(tags.getNumber());
        tagResponse.setPageSize(tags.getSize());
        tagResponse.setLast(tagResponse.isLast());
        tagResponse.setTotalPages(tags.getTotalPages());
        tagResponse.setTotalElements(tags.getTotalElements());

        return tagResponse;
    }

}