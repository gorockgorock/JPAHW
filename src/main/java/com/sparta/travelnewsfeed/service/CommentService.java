package com.sparta.travelnewsfeed.service;

import com.sparta.travelnewsfeed.dto.request.CommentCreateRequestDto;
import com.sparta.travelnewsfeed.dto.request.CommentUpdateRequestDto;
import com.sparta.travelnewsfeed.dto.response.CommentResponseDto;
import com.sparta.travelnewsfeed.entity.Comment;
import com.sparta.travelnewsfeed.entity.Post;
import com.sparta.travelnewsfeed.repository.CommentRepository;
import com.sparta.travelnewsfeed.repository.PostRepository;
import com.sparta.travelnewsfeed.repository.UserRepository;
import com.sparta.travelnewsfeed.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CommentResponseDto createComment(CommentCreateRequestDto dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시물 을 찾을 수 없습니다. id: " + dto.getPostId()));
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. 이름: " + dto.getUsername()));

        Comment comment = new Comment();
        comment.setText(dto.getText());
        comment.setPost(post);
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(
                savedComment.getId(),
                savedComment.getText(),
                savedComment.getCreatedAt(),
                savedComment.getUpdatedAt(),
                user.getUsername()
        );
    }

    public CommentResponseDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("댓글 id를 찾을 수 없습니다. id: " + id));
        String username = comment.getUser().getUsername();
        return new CommentResponseDto(comment.getId(), comment.getText(), comment.getCreatedAt(), comment.getUpdatedAt(), username);
    }

    public List<CommentResponseDto> findAllComments() {
        List<Comment> comments = commentRepository.findAllByOrderByCreatedAtDesc();
        return comments.stream()
                .map(comment -> new CommentResponseDto(comment.getId(), comment.getText(), comment.getCreatedAt(), comment.getUpdatedAt(), comment.getUser().getUsername()))
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentUpdateRequestDto dto, String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다.: " + username));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호");
        }

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("id를 찾을 수 없습니다. id: " + id));

        if (!comment.getUser().equals(user)) {
            throw new SecurityException("자신의 댓글만 수정할 수 있습니다.");
        }

        comment.setText(dto.getText());
        Comment updatedComment = commentRepository.save(comment);

        return new CommentResponseDto(updatedComment.getId(), updatedComment.getText(),
                updatedComment.getCreatedAt(), updatedComment.getUpdatedAt(), user.getUsername());
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("댓글 id를 찾을 수 없습니다. id: " + id));
        commentRepository.delete(comment);
    }
}
