package com.sparta.travelnewsfeed.service;

import com.sparta.travelnewsfeed.common.enumeration.Category;
import com.sparta.travelnewsfeed.dto.request.PostCreateRequestDto;
import com.sparta.travelnewsfeed.dto.request.PostUpdateRequestDto;
import com.sparta.travelnewsfeed.dto.response.PostCreateResponseDto;
import com.sparta.travelnewsfeed.dto.response.PostUpdateResponseDto;
import com.sparta.travelnewsfeed.entity.Post;
import com.sparta.travelnewsfeed.repository.PostRepository;
import com.sparta.travelnewsfeed.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    void createPostTest() {
        // Given
        User user = new User();
        PostCreateRequestDto postCreateRequestDto = new PostCreateRequestDto("test","test", Category.AFTER);
        Post post = new Post(user, postCreateRequestDto);
        given(postRepository.save(any(Post.class))).willReturn(post);

        // When
        PostCreateResponseDto result = postService.createPost(user, postCreateRequestDto);

        // Then
        assertNotNull(result);
        assertEquals(post.getPostId(), result.getPostId());
    }

    @Test
    void updatePostTest() {
        // Given
        User user = new User("test","test@test.com","1234567","0101234567");
        PostCreateRequestDto postCreateRequestDto = new PostCreateRequestDto("test","test", Category.AFTER);

        Long postId = 1L;
        Post post = new Post(user,postCreateRequestDto);


        PostUpdateRequestDto postUpdateRequestDto = new PostUpdateRequestDto("test1","test1",Category.BEFORE_PLAN,"1234567");

        given(postRepository.findById(postId)).willReturn(Optional.of(post));
        given(passwordEncoder.matches(postUpdateRequestDto.getPassword(), user.getPassword())).willReturn(true);

        // When
        PostUpdateResponseDto result = postService.update(user, postId, postUpdateRequestDto);

        // Then
        assertNotNull(result);
        assertEquals(post.getTitle(), result.getTitle());
    }


}