package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.CommentResponse;
import com.springboot.blog.service.CommentService;
import com.springboot.blog.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long id, @RequestBody CommentDto commentDto) {
        CommentDto newComment = commentService.createComment(id, commentDto);
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> getCommentsByPostId(
            @PathVariable long postId,
            @RequestParam(value="pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy
            ) {
        CommentResponse commentDtoList = commentService.getCommentsByPostId(postId, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(commentDtoList);
    }
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable long postId, @PathVariable long commentId) {
        CommentDto commentDto = commentService.getCommentById(postId, commentId);
        return ResponseEntity.ok(commentDto);
    }
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable long postId, @PathVariable long commentId, @RequestBody CommentDto commentDto) {
        CommentDto newComment = commentService.updateComment(postId, commentId, commentDto);
        return ResponseEntity.ok(newComment);
    }
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String>  deleteComment(@PathVariable long postId, @PathVariable long commentId) {
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
