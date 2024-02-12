package com.sparta.travelnewsfeed.dto.request;

import com.sparta.travelnewsfeed.common.enumeration.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {
    private String title;
    private String content;
    private Category category;
    private LocalDateTime modifiedAt;
}