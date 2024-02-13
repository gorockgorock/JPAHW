package com.sparta.travelnewsfeed.controller;

import com.sparta.travelnewsfeed.dto.request.PostCreateRequestDto;
import com.sparta.travelnewsfeed.dto.request.PostUpdateRequestDto;
import com.sparta.travelnewsfeed.dto.response.PostCreateResponseDto;
import com.sparta.travelnewsfeed.dto.response.PostReadResponseDto;
import com.sparta.travelnewsfeed.service.PostService;
import com.sparta.travelnewsfeed.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/travel/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostCreateResponseDto createPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostCreateRequestDto postCreateRequestDto){
        return postService.createPost(userDetails.getUser(),postCreateRequestDto);
    }

    @GetMapping
    public List<PostReadResponseDto> readAll(){
        return postService.readAll();
    }

    @GetMapping("/{postId}")
    public PostReadResponseDto readDetail(@PathVariable Long postId){
        return postService.readDetail(postId);
    }

    @PutMapping("/{postId}")
    public PostReadResponseDto update(@PathVariable Long postId, @RequestBody PostUpdateRequestDto postUpdateRequestDto){
        return postService.update(postId, postUpdateRequestDto);
    }

    @DeleteMapping("/{postId}")
    public void delete(@PathVariable Long postId){
        postService.delete(postId);
    }

}
