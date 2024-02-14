package com.sparta.travelnewsfeed.dto.response;

import com.sparta.travelnewsfeed.common.enumeration.Category;
import com.sparta.travelnewsfeed.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@NoArgsConstructor
public class PostUpdateResponseDto {
    private Long postId;
    private String title;
    private String content;
    private Category category;
    private HttpStatusCode code;
    private String message;

    public PostUpdateResponseDto(Post post){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.category = post.getCategory();
        this.code = HttpStatus.OK;
        this.message ="수정이 완료되었습니다.";
    }

    public PostUpdateResponseDto(HttpStatusCode code, String message){
        this.code = code;
        this.message = message;
    }
}