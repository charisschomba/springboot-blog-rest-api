package com.springboot.blog.controller;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;

import com.springboot.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    @GetMapping()
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> post = postService.getAllPosts();
        return ResponseEntity.ok(post);
    }
    @GetMapping("/{id}")
    ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long postId) {
        PostDto post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }
    @PutMapping("/{id}")
    ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable long id) {
       PostDto updatedPost = postService.updatePost(postDto, id);
       return ResponseEntity.ok(updatedPost);
    }
    @DeleteMapping("/{id}")
    ResponseEntity<String> deletePost(@PathVariable long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }
}
