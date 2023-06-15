package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.Tag;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.repository.TagRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper mapper;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, CategoryRepository categoryRepository, TagRepository tagRepository) {

        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }
    @Override
    public PostDto createPost(PostDto postDto) {

        // Convert DTO to Entity
        Post post = mapToEntity(postDto);
        Long categoryId = postDto.getCategoryId();
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
        if(postDto.getTagId() != null) {
           Tag tag = tagRepository.findById(postDto.getTagId()).orElseThrow(() -> new ResourceNotFoundException("Tag", "Id", postDto.getTagId()));
           post.setTag(tag);
        }
        post.setCategory(category);
        Post newPost = postRepository.save(post);

        //  Convert entity to DTO
        return mapToDto(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        // create Sort instance
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        return postResponse(posts);
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        Long categoryId = postDto.getCategoryId();
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
        post.setCategory(category);
        if(postDto.getTagId() != null) {
            Tag tag = tagRepository.findById(postDto.getTagId()).orElseThrow(() -> new ResourceNotFoundException("Tag", "Id", postDto.getTagId()));
            post.setTag(tag);
        }
        Post updatedPost = postRepository.save(post);

        return mapToDto(updatedPost);


    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.deleteById(id);
    }

    @Override
    public PostResponse getPostsByCategory(Long categoryId, int pageNo, int pageSize, String sortBy, String sortDir) {
        // create Sort instance
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findByCategoryId(categoryId, pageable);
        PostResponse postResponse = postResponse(posts);

        return postResponse;
    }

    @Override
    public PostResponse getPostsTagByTagId(Long tagId, int pageNo, int pageSize, String sortBy, String sortDir) {
        // create Sort instance
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findByCategoryId(tagId, pageable);
        PostResponse postResponse = postResponse(posts);

        return postResponse;
    }

    //  Convert entity to DTO
    private PostDto mapToDto(Post post) {
        PostDto postDto = mapper.map(post, PostDto.class);
//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return postDto;
    }

    // Convert DTO to Entity
    private Post mapToEntity(PostDto postDto) {
        Post post = mapper.map(postDto, Post.class);
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
    private PostResponse postResponse(Page<Post> posts) {
        //get content for page object
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content =  listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse= new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setLast(postResponse.isLast());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());

        return postResponse;
    }

}
