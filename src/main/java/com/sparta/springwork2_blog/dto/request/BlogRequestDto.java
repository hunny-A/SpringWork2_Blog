package com.sparta.springwork2_blog.dto.request;

import com.sparta.springwork2_blog.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BlogRequestDto {
    private String date;
    private Long id;
    private String title;
    private String username;
    private String contents;
    private User user;

}
