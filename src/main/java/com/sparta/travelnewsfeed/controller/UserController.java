package com.sparta.travelnewsfeed.controller;

import com.sparta.travelnewsfeed.dto.request.SignupRequestDto;
import com.sparta.travelnewsfeed.dto.response.CommonResponseDto;
import com.sparta.travelnewsfeed.dto.response.UserResponseDto;
import com.sparta.travelnewsfeed.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/travel/users")
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
    @GetMapping("/{username}")
    public ResponseEntity<CommonResponseDto> getUser(@PathVariable String username) {
        try {
            UserResponseDto responseDTO = userService.getUserDto(username);
            return ResponseEntity.ok().body(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

//    @PutMapping("/update")
//    public ResponseEntity<CommonResponseDto> updateUser(@RequestHeader(
//            "PASSWORD") String password, @RequestBody UserRequestDto userRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        try {
//            UserResponseDto responseDTO = userService.updateUser(password, userRequestDto, userDetails.getUser());
//            return ResponseEntity.ok().body(responseDTO);
//        } catch (RejectedExecutionException | IllegalArgumentException ex) {
//            return ResponseEntity.badRequest().body(new CommonResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
//        }
//    }

}


