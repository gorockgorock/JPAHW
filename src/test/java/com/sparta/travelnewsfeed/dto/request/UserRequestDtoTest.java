package com.sparta.travelnewsfeed.dto.request;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserRequestDtoTest {

    private LocalValidatorFactoryBean validator = createValidator();

    private LocalValidatorFactoryBean createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }
    @Test
    @DisplayName("유저 정보 세팅 성공")
    void test1() {
        // Given
        String testUsername = "testUser";
        String testEmail = "test@gmail.com";
        String testPassword = "123456789";
        String testPhoneNumber = "01012345678";


        // When
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setUsername(testUsername);
        requestDto.setEmail(testEmail);
        requestDto.setPassword(testPassword);
        requestDto.setPhone_number(testPhoneNumber);


        // Then

        assertEquals("testUser", requestDto.getUsername());
        assertEquals("test@gmail.com", requestDto.getEmail());
        assertEquals("123456789", requestDto.getPassword());
        assertEquals("01012345678", requestDto.getPhone_number());
    }

    @Test
    @DisplayName("유저정보 세팅 실패")
    void test2() {
        // Given
        String testUsername = "te";
        String testEmail = "test@gmail.com";
        String testPassword = "123456789";
        String testPhoneNumber = "01012345678";


        // When
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setUsername(testUsername);
        requestDto.setEmail(testEmail);
        requestDto.setPassword(testPassword);
        requestDto.setPhone_number(testPhoneNumber);


        // Then

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(requestDto);
        assertFalse(violations.isEmpty());
        assertEquals("4자 이상 10자 이하영어 대소문자나 숫자만 가능합니다", violations.iterator().next().getMessage());

    }
}

