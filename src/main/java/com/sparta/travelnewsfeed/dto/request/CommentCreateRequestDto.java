package com.sparta.travelnewsfeed.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentCreateRequestDto {
    private String text;
    private Long postId; // Ensure this attribute is included to link to the Post entity
}
