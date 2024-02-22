package com.sparta.travelnewsfeed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.travelnewsfeed.config.WebSecurityConfig;
import com.sparta.travelnewsfeed.dto.request.SignupRequestDto;
import com.sparta.travelnewsfeed.dto.request.UserRequestDto;
import com.sparta.travelnewsfeed.dto.response.UserResponseDto;
import com.sparta.travelnewsfeed.mvc.MockSpringSecurityFilter;
import com.sparta.travelnewsfeed.service.UserService;
import com.sparta.travelnewsfeed.user.User;
import com.sparta.travelnewsfeed.user.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = {UserController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class //내가 만든 컨피그 사용 안함
                )
        }
)
class UserControllerTest {

    private MockMvc mvc;

    private Principal mockPrincipal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build(); //
    }

    private void mockUserSetup() {
        // Mock 테스트 유져 생성
        String username = "test1234";
        String password = "12345678";
        String email = "test@test.com";
        String phone_number = "01012345678";

        User testUser = new User(username, password, email, phone_number);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
    }

    @Test
    public void signupTest() throws Exception {
        // Given
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername("test1234");
        signupRequestDto.setPassword("123456789");
        signupRequestDto.setEmail("test@test.com");
        signupRequestDto.setPhone_number("1234567890");

        String jsonRequest = objectMapper.writeValueAsString(signupRequestDto);

        // When & Then
        mvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());
        verify(userService, times(1)).signup(any(SignupRequestDto.class));
    }

    @Test
    @WithMockUser
    void updateUserTest() throws Exception {
        // Given
        this.mockUserSetup();
        UserRequestDto userRequestDto = new UserRequestDto();
        UserResponseDto userResponseDto = new UserResponseDto();

        given(userService.updateUser(any(User.class), any(UserRequestDto.class))).willReturn(userResponseDto);

        String jsonRequest = objectMapper.writeValueAsString(userRequestDto);

        // When & Then
        mvc.perform(put("/api/users/update")
                        .principal(mockPrincipal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());


        verify(userService, times(1)).updateUser(any(User.class), any(UserRequestDto.class));
    }



}