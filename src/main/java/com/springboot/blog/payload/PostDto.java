package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "Post DTO model"
)
public class PostDto {
    private long id;
    @NotEmpty
    @Size(min = 2, message = "Post title must have at least 2 characters")
    @Schema(description = "Blog post title")
    private String title;
    @NotEmpty
    @Size(min = 5, message = "Post title must have at least 10 characters")
    @Schema(description = "Blog post description")
    private String description;
    @NotEmpty
    @Schema(description = "Blog post content")
    private String content;
    private Set<CommentDto> comments;

    @NotNull
    @Schema(description = "Category for a post")
    private Long categoryId;

    @Schema(description = "Tag for a post ")
    private Long tagId;
}
