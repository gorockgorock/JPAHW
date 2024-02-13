package com.sparta.travelnewsfeed.service;

import com.sparta.travelnewsfeed.dto.request.PostCreateRequestDto;
import com.sparta.travelnewsfeed.dto.request.PostUpdateRequestDto;
import com.sparta.travelnewsfeed.dto.response.PostCreateResponseDto;
import com.sparta.travelnewsfeed.dto.response.PostReadResponseDto;
import com.sparta.travelnewsfeed.entity.Post;
import com.sparta.travelnewsfeed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostCreateResponseDto createPost(PostCreateRequestDto postCreateRequestDto) {
        Post post = postRepository.save(new Post(postCreateRequestDto));
        return new PostCreateResponseDto(post);
    }

    public List<PostReadResponseDto> readAll() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostReadResponseDto> postListToDto = postList.stream()
                .map(m -> new PostReadResponseDto(m)).collect(Collectors.toList());
        return postListToDto;
    }

    public PostReadResponseDto readDetail(Long postId) throws NoSuchElementException {
        Post post = postRepository.findById(postId).orElseThrow(NoSuchElementException::new);
        return new PostReadResponseDto(post);
    }

    @Transactional
    public PostReadResponseDto update(Long postId, PostUpdateRequestDto postUpdateRequestDto) {
        Post post = postRepository.findById(postId).orElseThrow(NoSuchElementException::new);
        post.update(postUpdateRequestDto);
        return new PostReadResponseDto(post);
    }

    @Transactional
    public void delete(Long postId) {
        postRepository.findById(postId).orElseThrow(NoSuchElementException::new);
        postRepository.deleteById(postId);
    }
}