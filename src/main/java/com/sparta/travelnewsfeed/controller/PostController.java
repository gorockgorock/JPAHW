package com.sparta.travelnewsfeed.controller;

import com.sparta.travelnewsfeed.dto.request.PostCreateRequestDto;
import com.sparta.travelnewsfeed.dto.request.PostUpdateRequestDto;
import com.sparta.travelnewsfeed.dto.response.PostCreateResponseDto;
import com.sparta.travelnewsfeed.dto.response.PostReadResponseDto;
import com.sparta.travelnewsfeed.dto.response.PostUpdateResponseDto;
import com.sparta.travelnewsfeed.service.PostService;
import com.sparta.travelnewsfeed.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostCreateResponseDto> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostCreateRequestDto postCreateRequestDto){
        return ResponseEntity.ok().body(postService.createPost(userDetails.getUser(),postCreateRequestDto));
    }

    @GetMapping("/read")
    public ResponseEntity<List<PostReadResponseDto>> readAll(){
        return ResponseEntity.ok().body(postService.readAll());
    }

    @GetMapping("/read/{postId}")
    public ResponseEntity<PostReadResponseDto> readDetail(@PathVariable Long postId){
        return ResponseEntity.ok().body(postService.readDetail(postId));
    }

    @GetMapping("/read/{keyword}")
    public Page<PostReadResponseDto> searchByKeyword(
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @PathVariable String keyword) {
        return postService.searchByKeyword(keyword,
            page-1, size);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostUpdateResponseDto> update(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId, @RequestBody PostUpdateRequestDto postUpdateRequestDto){
        return ResponseEntity.ok().body(postService.update(userDetails.getUser(), postId, postUpdateRequestDto));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserDetailsImpl userDetails,@RequestBody String password, @PathVariable Long postId){
        postService.delete(userDetails.getUser(), password, postId);
        return ResponseEntity.ok().build();
    }

}
