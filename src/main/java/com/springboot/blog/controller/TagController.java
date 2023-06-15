package com.springboot.blog.controller;

import com.springboot.blog.payload.TagDto;
import com.springboot.blog.payload.TagResponse;
import com.springboot.blog.service.TagService;
import com.springboot.blog.utils.AppConstants;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TagDto> createTag(@Valid @RequestBody TagDto tagDto) {
        TagDto newTag = tagService.createTag(tagDto);
        return new ResponseEntity<>(newTag, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<TagResponse> getTags(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir
    ) {
        TagResponse response =this.tagService.getTags(pageNo, pageSize, sortBy, sortDir);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{tagId}")
    public ResponseEntity<TagDto> editTag(@Valid @RequestBody TagDto tagDto, @PathVariable Long tagId) {
        TagDto updatedTag = this.tagService.editTag(tagId, tagDto);

        return ResponseEntity.ok(updatedTag);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{tagId}")
    public ResponseEntity<String> deleteTag(@PathVariable Long tagId) {
        String response = this.tagService.deleteTag(tagId);
        return ResponseEntity.ok(response);
    }
}
