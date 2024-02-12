package com.sparta.travelnewsfeed.entity;

import com.sparta.travelnewsfeed.common.enumeration.Category;
import com.sparta.travelnewsfeed.dto.request.PostCreateRequestDto;
import com.sparta.travelnewsfeed.entity.common.DateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "post")
public class Post extends DateTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long postId;

    @NotNull
    @Column(nullable = false)
    public String title;

    @NotNull
    @Column(nullable = false)
    public String content;

    @Enumerated(EnumType.STRING)
    public Category category;

    public Post(PostCreateRequestDto postCreateRequestDto){
        this.title = postCreateRequestDto.getTitle();
        this.content = postCreateRequestDto.getContent();
        this.category = postCreateRequestDto.getCategory();
    }
}
