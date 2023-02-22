package com.sparta.springwork2_blog.service;


import com.sparta.springwork2_blog.dto.request.BlogRequestDto;
import com.sparta.springwork2_blog.dto.response.BlogResponseDto;
import com.sparta.springwork2_blog.dto.response.MegResponseDto;
import com.sparta.springwork2_blog.entity.Blog;
import com.sparta.springwork2_blog.entity.User;
import com.sparta.springwork2_blog.entity.UserRoleEnum;
import com.sparta.springwork2_blog.jwt.JwtUtil;
import com.sparta.springwork2_blog.repository.BlogRepository;
import com.sparta.springwork2_blog.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    /* 전체 게시글 목록 조회 */
    @Transactional(readOnly = true)
    public List<BlogResponseDto> getBlogs() {
        /*return blogRepository.findAllByOrderByCreatedAtDesc();
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시글 목록 조회
        if(token != null){
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            }else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    ()->new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            List<BlogResponseDto> list = new ArrayList<>();
            List<Blog> blogList = blogRepository.findAllByUserId(user.getId());

            for(Blog blog : blogList){
                BlogResponseDto bDto = new BlogResponseDto(blog);
                list.add(bDto);
            }

            return list;
        }
        return null;*/
            List<BlogResponseDto> list = new ArrayList<>();
            List<Blog> blogList = blogRepository.findAllByOrderByCreatedAtDesc();

            for(Blog blog : blogList){
                BlogResponseDto bDto = new BlogResponseDto(blog);
                list.add(bDto);
            }

            return list;
    }


    /* 게시글 작성 */
    @Transactional
    public BlogResponseDto createBlog(User user, BlogRequestDto requestDto, HttpServletRequest request){   //HttpServletRequest : HTTP 요청 메시지 파싱
       // token방식
        /*// Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) { //  토큰 검증
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }*/
        /*// 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );*/
        /*            return new BlogResponseDto(blogRepository.saveAndFlush(Blog.builder()
                    .blogrequestDto(requestDto)
                    .user(user)
                    .build()));
*/

        if(user.getRole() != null){
            Blog blog = blogRepository.save(new Blog(requestDto,user));
            return new BlogResponseDto(blog);

        }
        throw new IllegalArgumentException("로그인이 필요합니다.");
    }



    /* 선택 게시글 조회 */
    @Transactional(readOnly = true)
    public BlogResponseDto getBlogfindById(Long id, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시글 목록 조회
        if(token != null){
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            }else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    ()->new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Blog blog = blogRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
            );
            return new BlogResponseDto(blog);
        }
        return null;
    }


    /* 선택 게시글 수정 */
    @Transactional
    public BlogResponseDto update(Long id, User user, BlogRequestDto requestDto, HttpServletRequest request) {

        /*
         * 1. UDI에서 심사 완료된 유저명과 비교
         * 2. 같을 때 수정 가능
         * 3. 다를 때 예외처리 : 실사용자가 아닙니다."
         * */

        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
        );

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("authentication.getName() : " + authentication.getName());
//        System.out.println("(blog.getUser().getUsername() : "+(blog.getUser().getUsername()));

        if(blog.getUser().getUsername().equals(user.getUsername()) || user.getRole() == UserRoleEnum.ADMIN){
            // 💥 영속성 컨텍스트, 더티 체킹 확인
            blog.update(requestDto,user);
        } else {
            throw new IllegalArgumentException("실사용자가 아닙니다.");
        }


        return new BlogResponseDto(blog);

        /* //토큰 비교 방식

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시글 수정
        if(token != null){
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            }else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    ()->new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Blog blog = blogRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
            );

            blog.update(requestDto,user);

            return new BlogResponseDto(blog);
        }else {
            return null;
        }*/
    }



    /* 선택 게시글 삭제 */
    @Transactional
    public ResponseEntity<MegResponseDto> delete(Long id, User user, HttpServletRequest request) {

        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
        );

        if(blog.getUser().getUsername().equals(user.getUsername()) || user.getRole() == UserRoleEnum.ADMIN){
            // 📌 entity와 Id
            blogRepository.delete(blog);
        } else {
            return ResponseEntity.ok()
                    .body(MegResponseDto.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .msg("실 사용자가 아닙니다.")
                            .build());
        }

        return ResponseEntity.ok()
                .body(MegResponseDto.builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("게시글 삭제 성공")
                        .build());
    }

        /* //토큰 비교 방식

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시글 삭제
        if(token != null){
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            }else {
                throw new IllegalArgumentException("Token Error");
            }

            Blog blog = blogRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
            );

            blogRepository.delete(blog);

            return ResponseEntity.ok()
                    .body(MegResponseDto.builder()
                            .statusCode(HttpStatus.OK.value())
                            .msg("게시글 삭제 성공")
                            .build());
        }else {
            return null;
        }*/
}
