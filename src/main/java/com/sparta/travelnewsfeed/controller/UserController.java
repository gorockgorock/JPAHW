package com.sparta.travelnewsfeed.controller;

import com.sparta.travelnewsfeed.dto.request.SignupRequestDto;
import com.sparta.travelnewsfeed.dto.response.CommonResponseDto;
import com.sparta.travelnewsfeed.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/travel/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        try {
            userService.signup(signupRequestDto);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new CommonResponseDto("중복된 사용자이름 입니다.", HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity
                .status(HttpStatus.CREATED.value())
                .body(new CommonResponseDto("회원가입 되었습니다", HttpStatus.CREATED.value()));
    }
}


