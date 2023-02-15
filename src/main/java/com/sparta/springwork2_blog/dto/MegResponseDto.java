package com.sparta.springwork2_blog.dto;


import lombok.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class MegResponseDto {
    private String msg;
    private Integer statusCode;
}
