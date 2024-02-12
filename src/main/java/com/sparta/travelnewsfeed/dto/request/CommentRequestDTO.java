package com.sparta.travelnewsfeed.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class CommentRequestDTO {
    private String text;
    private Long postId;
    private String username;
    private String password;
}
