package com.sparta.travelnewsfeed.repository;

import com.sparta.travelnewsfeed.dto.request.CommentCreateRequestDto;
import com.sparta.travelnewsfeed.entity.Comment;
import com.sparta.travelnewsfeed.entity.Post;
import com.sparta.travelnewsfeed.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;


import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;



    @MockBean
    CommentCreateRequestDto dto;

    @BeforeEach
    @DisplayName("시간 순 정렬 조회 테스트")
    public void setUp() {

        //given
        User user = new User("testuser","test@test.com","12345678","123456789");
        Post post = createPost("test","test",user);
        userRepository.save(user);
        postRepository.save(post);


        Comment comment1 = createComment("Test 1", post, user, LocalDateTime.now().minusHours(1));
        Comment comment2 = createComment("Test 2", post, user, LocalDateTime.now().minusHours(2));
        Comment comment3 = createComment("Test 3", post, user, LocalDateTime.now());

       commentRepository.save(comment1);
       commentRepository.save(comment2);
       commentRepository.save(comment3);

    }

    private Comment createComment(String text, Post post, User user, LocalDateTime createdAt) {
        Comment comment = new Comment();
        ReflectionTestUtils.setField(comment, "text", text);
        ReflectionTestUtils.setField(comment, "post", post);
        ReflectionTestUtils.setField(comment, "user", user);
        ReflectionTestUtils.setField(comment, "createdAt", createdAt);
        return comment;
    }

    private Post createPost(String title, String content, User user) {
       Post post = new Post();
        ReflectionTestUtils.setField(post, "title",title);
        ReflectionTestUtils.setField(post, "content", content);
        ReflectionTestUtils.setField(post, "user", user);

        return post;
    }

    @Test
    public void findAllByOrderByCreatedAtDescTest() {
        // When
        List<Comment> comments = commentRepository.findAllByOrderByCreatedAtDesc();

        // Then
        assertNotNull(comments);
        assertTrue(comments.get(0).getCreatedAt().isAfter(comments.get(1).getCreatedAt()));
        assertTrue(comments.get(1).getCreatedAt().isAfter(comments.get(2).getCreatedAt()));
    }
}