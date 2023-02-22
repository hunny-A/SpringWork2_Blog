package com.sparta.springwork2_blog.service;


import com.sparta.springwork2_blog.dto.request.LoginRequestDto;
import com.sparta.springwork2_blog.dto.response.MegResponseDto;
import com.sparta.springwork2_blog.dto.request.SignupRequestDto;
import com.sparta.springwork2_blog.entity.User;
import com.sparta.springwork2_blog.entity.UserRoleEnum;
import com.sparta.springwork2_blog.jwt.JwtUtil;
import com.sparta.springwork2_blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    /* 회원가입 */
    @Transactional
    public ResponseEntity<MegResponseDto> signup(SignupRequestDto signupRequestDto, BindingResult result) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();

        //  유효성 검사 통과 못 한 경우
        if (result.hasErrors()) {   // Return if there were any errors.
            //throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
            return ResponseEntity.badRequest()
                    .body(MegResponseDto.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())   //bad_request : 400번 반환, ok : 200번 반환
                            .msg(result.getAllErrors().get(0)
                                    .getDefaultMessage())
                                    .build());
        }

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if(found.isPresent()){
            return ResponseEntity.badRequest()
                    .body(MegResponseDto.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .msg("중복된 사용자가 존재합니다.")
                            .build());
        }

        // 관리자 등록
        UserRoleEnum role = UserRoleEnum.USER;

        if(!signupRequestDto.getAdminToken().isEmpty()){    //AdminToken이 비어있지 않다면 실행
            if(!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)){
                return ResponseEntity.badRequest()
                        .body(MegResponseDto.builder()
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .msg("관리자 암호가 틀려 등록이 불가능합니다.")
                                .build());
            }
            role = UserRoleEnum.ADMIN;
        }


        userRepository.save(User.builder()
                .username(username)
                .password(password)
                .role(role)
                .build());

        return ResponseEntity.ok(MegResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .msg("회원가입 성공")
                .build());
    }


    /* 로그인 */
    @Transactional(readOnly = true)
    public ResponseEntity<MegResponseDto> login(LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()){
            return ResponseEntity.badRequest()
                    .body(MegResponseDto.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .msg("등록되지 않았습니다.")
                            .build());
        }

        if(!user.get().getPassword().equals(password)) {
            return ResponseEntity.badRequest()
                    .body(MegResponseDto.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .msg("비밀번호가 틀렸습니다.")
                            .build());
        }

        // status : badRequest
        // body : MegResponseDto -> Code, msg
        HttpHeaders headers = new HttpHeaders();
        headers.set(JwtUtil.AUTHORIZATION_HEADER,jwtUtil.createToken(user.get().getUsername(), user.get().getRole()));
        headers.set("AdminToken",String.valueOf(user.get().getRole()));

        return ResponseEntity.ok()
                .headers(headers)
                .body(MegResponseDto.builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("로그인이 되었습니다.")
                        .build());

    }
}
