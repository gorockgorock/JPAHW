package com.sparta.travelnewsfeed.service;

import com.sparta.travelnewsfeed.dto.request.PostCreateRequestDto;
import com.sparta.travelnewsfeed.dto.request.PostUpdateRequestDto;
import com.sparta.travelnewsfeed.dto.response.PostCreateResponseDto;
import com.sparta.travelnewsfeed.dto.response.PostReadResponseDto;
import com.sparta.travelnewsfeed.dto.response.PostUpdateResponseDto;
import com.sparta.travelnewsfeed.entity.Post;
import com.sparta.travelnewsfeed.repository.PostRepository;
import com.sparta.travelnewsfeed.user.User;
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
    public PostCreateResponseDto createPost(User user, PostCreateRequestDto postCreateRequestDto) {
        Post post = postRepository.save(new Post(user,postCreateRequestDto));
        return new PostCreateResponseDto(post);
    }

    public List<PostReadResponseDto> readAll() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostReadResponseDto> postListToDto = postList.stream()
                .map(m -> new PostReadResponseDto(m)).collect(Collectors.toList());
        return postListToDto;
    }

    public PostReadResponseDto readDetail(Long postId) throws NoSuchElementException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("해당 글을 찾을 수 없습니다."));
        return new PostReadResponseDto(post);
    }

    @Transactional
    public PostUpdateResponseDto update(User user, Long postId, PostUpdateRequestDto postUpdateRequestDto) throws NoSuchElementException{
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("해당 글을 찾을 수 없습니다."));
        post.update(user, postUpdateRequestDto);
        return new PostUpdateResponseDto(post);
    }

    @Transactional
    public void delete(User user, Long postId) throws NoSuchElementException {
        postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("해당 글을 찾을 수 없습니다."));
        postRepository.deleteByUserAndPostId(user, postId);
    }
}