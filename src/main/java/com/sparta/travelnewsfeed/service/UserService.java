package com.sparta.travelnewsfeed.service;

import com.sparta.travelnewsfeed.dto.request.SignupRequestDto;
import com.sparta.travelnewsfeed.dto.response.UserResponseDto;
import com.sparta.travelnewsfeed.entity.Category;
import com.sparta.travelnewsfeed.jwt.JwtUtil;
import com.sparta.travelnewsfeed.repository.UserRepository;
import com.sparta.travelnewsfeed.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public void signup (SignupRequestDto signupRequestDto){
        String username = signupRequestDto.getUsername();
        String email = signupRequestDto.getEmail();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String phone_number = signupRequestDto.getPhone_number();
        Category category = signupRequestDto.getCategory();

        if (userRepository.findByUsername(username).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 유저이름입니다");
        }
        User user = new User(username, email, password, phone_number,category);
        userRepository.save(user);

    }

    public UserResponseDto getUserDto(String username) {
        User user = getUser(username);
        return new UserResponseDto(user);
    }


    public User getUser(String username) {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 사용자입니다."));
    }

}
