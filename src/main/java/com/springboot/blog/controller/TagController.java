package com.springboot.blog.controller;

import com.springboot.blog.payload.TagDto;
import com.springboot.blog.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TagDto> createTag(@RequestBody TagDto tagDto) {
        TagDto newTag = tagService.createTag(tagDto);
        return new ResponseEntity<>(newTag, HttpStatus.CREATED);
    }
}
