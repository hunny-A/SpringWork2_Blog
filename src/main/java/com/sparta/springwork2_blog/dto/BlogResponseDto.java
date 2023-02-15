package com.sparta.springwork2_blog.dto;

import com.sparta.springwork2_blog.entity.Blog;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BlogResponseDto {
    private Long id;
    private String username;
    private String title;
    private String contents;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.username = blog.getUsername();
        this.title = blog.getTitle();
        this.contents = blog.getContents();
        this.createAt = blog.getCreatedAt();
        this.modifiedAt = blog.getModifiedAt();
    }
}
