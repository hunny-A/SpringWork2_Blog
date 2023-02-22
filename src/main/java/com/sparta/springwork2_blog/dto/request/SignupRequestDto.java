package com.sparta.springwork2_blog.dto.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter
@Getter
public class SignupRequestDto {

    @NotBlank
    @Pattern(regexp = "[a-z]+[a-z0-9]{3,9}$",message = "알파벳 소문자와 숫자를 이용해 최소 4문자 최대 10문자의 아이디를 만드세요.")
    private String username;

    @NotBlank
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{7,14}$",message = "알파벳 소문자와 대문자, 숫자를 이용해 최소 8문자 최대 15문자의 비밀번호를 만드세요.")
    private String password;
    private boolean admin = false;

    private String adminToken = "";
}
