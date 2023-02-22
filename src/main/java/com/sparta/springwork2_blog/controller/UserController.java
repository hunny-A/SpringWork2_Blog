package com.sparta.springwork2_blog.controller;


import com.sparta.springwork2_blog.dto.request.LoginRequestDto;
import com.sparta.springwork2_blog.dto.response.MegResponseDto;
import com.sparta.springwork2_blog.dto.request.SignupRequestDto;
import com.sparta.springwork2_blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<MegResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto, BindingResult result) {
        return userService.signup(signupRequestDto,result);
        //return "redirect:/api/user/login";
    }

    @PostMapping("/login")
    public ResponseEntity<MegResponseDto> login(@RequestBody LoginRequestDto RequestDto) {
        //userService.login(loginRequestDto, response);
        return userService.login(RequestDto);
    }

}
