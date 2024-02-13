package com.sparta.travelnewsfeed.dto.response;

import com.sparta.travelnewsfeed.common.enumeration.Category;
import com.sparta.travelnewsfeed.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateResponseDto {
    private Long postId;
    private String title;
    private String content;
    private Category category;

    public PostUpdateResponseDto(Post post){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.category = post.getCategory();
    }
}