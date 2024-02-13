package com.sparta.travelnewsfeed.dto.request;

import com.sparta.travelnewsfeed.entity.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserRequestDto {
    @NotBlank
    @Pattern(regexp = "^[a-zA-z0-9]{4,10}", message = "4자 이상 10자 이하영어 대소문자나 숫자만 가능합니다")
    //@(min=4, max=10, message = "4글자이상 10글자 이하로 입력해주세요")
    private String username;
    @Email
    @NotBlank
    private String email;
    @Pattern(regexp = "^[0-9]{3,20}", message = "숫자만 입력해주세요")
    private String phone_number;
    @Enumerated(value = EnumType.STRING)
    private Category category;
}
