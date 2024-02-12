package com.sparta.travelnewsfeed.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private String text;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long postId;
}
