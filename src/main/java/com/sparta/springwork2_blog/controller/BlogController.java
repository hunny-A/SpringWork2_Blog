package com.sparta.springwork2_blog.controller;


import com.sparta.springwork2_blog.dto.BlogRequestDto;
import com.sparta.springwork2_blog.dto.BlogResponseDto;
import com.sparta.springwork2_blog.dto.MegResponseDto;
import com.sparta.springwork2_blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BlogController {

    private final BlogService blogService;

    /* 게시글 추가 */
    @PostMapping("/blogs")
    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto, HttpServletRequest request){
        return blogService.createBlog(requestDto,request);
    }

    /* 전체 게시글 조회 */
    @GetMapping("/blogs")
    public List<BlogResponseDto> getBlogs() {
        return blogService.getBlogs();
    }

    /* 선택 게시글 조회*/
    @GetMapping("/blogs/{id}")
    public BlogResponseDto getBlogfindById(@PathVariable Long id, HttpServletRequest request){
        return blogService.getBlogfindById(id, request);
    }

    /* 선택 게시글 수정 */
    @PutMapping("/blogs/{id}")
    public BlogResponseDto updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto, HttpServletRequest request){
        return blogService.update(id, requestDto,request);
    }

    /* 선택 게시글 삭제 */
    @DeleteMapping("/blogs/{id}")
    public ResponseEntity<MegResponseDto> deleteBlog(@PathVariable Long id, HttpServletRequest request){
        return blogService.delete(id, request);
    }

}
