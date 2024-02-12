package com.sparta.travelnewsfeed.dto.request;

import com.sparta.travelnewsfeed.common.enumeration.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequestDto {
    private String title;
    private String content;
    private Category category;
}
