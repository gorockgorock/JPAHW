package com.sparta.travelnewsfeed.controller;

import com.sparta.travelnewsfeed.dto.request.PostCreateRequestDto;
import com.sparta.travelnewsfeed.dto.response.PostCreateResponseDto;
import com.sparta.travelnewsfeed.dto.response.PostReadResponseDto;
import com.sparta.travelnewsfeed.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/travel/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostCreateResponseDto createPost(@RequestBody PostCreateRequestDto postCreateRequestDto){
        return postService.createPost(postCreateRequestDto);
    }

    @GetMapping
    public List<PostReadResponseDto> readAll(){
        return postService.readAll();
    }

    @GetMapping("/{postId}")
    public PostReadResponseDto readDetail(@PathVariable Long postId){
        return postService.readDetail(postId);
    }
}
