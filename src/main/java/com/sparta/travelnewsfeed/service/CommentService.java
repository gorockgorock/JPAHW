package com.sparta.travelnewsfeed.service;

import com.sparta.travelnewsfeed.dto.request.CommentCreateRequestDto;
import com.sparta.travelnewsfeed.dto.request.CommentUpdateRequestDto;
import com.sparta.travelnewsfeed.dto.response.CommentResponseDto;
import com.sparta.travelnewsfeed.entity.Comment;
import com.sparta.travelnewsfeed.entity.Post;
import com.sparta.travelnewsfeed.repository.CommentRepository;
import com.sparta.travelnewsfeed.repository.PostRepository;
import com.sparta.travelnewsfeed.user.User;
import lombok.RequiredArgsConstructor;
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

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CommentResponseDto createComment(Long postId, User user, CommentCreateRequestDto dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        Comment comment = new Comment(post, user, dto);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
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
    public CommentResponseDto updateComment(User user, Long id, CommentUpdateRequestDto dto) {

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호");
        }

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("id를 찾을 수 없습니다. id: " + id));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new SecurityException("자신의 댓글만 수정할 수 있습니다.");
        }

        comment.setText(dto.getText());
        Comment updatedComment = commentRepository.save(comment);

        return new CommentResponseDto(updatedComment.getId(), updatedComment.getText(),
                updatedComment.getCreatedAt(), updatedComment.getUpdatedAt(), user.getUsername());
    }

    @Transactional
    public void deleteComment(User user, Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("댓글 id를 찾을 수 없습니다. id: " + id));

        if (comment.getUser().getUsername().equals(user.getUsername())) {
            commentRepository.delete(comment);
        } else {
            throw new SecurityException("자신의 댓글만 삭제할 수 있습니다.");
        }
    }
}
