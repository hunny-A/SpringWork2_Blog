package com.sparta.springwork2_blog.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class LoginRequestDto {
    private String username;
    private String password;
}
