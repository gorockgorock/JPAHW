package com.sparta.travelnewsfeed.service;

import com.sparta.travelnewsfeed.dto.request.CommentCreateRequestDto;
import com.sparta.travelnewsfeed.dto.request.CommentUpdateRequestDto;
import com.sparta.travelnewsfeed.dto.response.CommentResponseDto;
import com.sparta.travelnewsfeed.entity.Comment;
import com.sparta.travelnewsfeed.entity.Post;
import com.sparta.travelnewsfeed.repository.CommentRepository;
import com.sparta.travelnewsfeed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Transactional
    public CommentResponseDto createComment(CommentCreateRequestDto dto) {
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new RuntimeException("게시물 을 찾을 수 없습니다. id: " + dto.getPostId()));

        Comment comment = new Comment();
        comment.setText(dto.getText());
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(
                savedComment.getId(),
                savedComment.getText(),
                savedComment.getCreatedAt(),
                savedComment.getUpdatedAt()
        );
    }

    public CommentResponseDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found for id: " + id));
        return new CommentResponseDto(comment.getId(), comment.getText(), comment.getCreatedAt(), comment.getUpdatedAt());
    }

    public List<CommentResponseDto> findAllComments() {
        List<Comment> comments = commentRepository.findAllByOrderByCreatedAtDesc();
        return comments.stream()
                .map(comment -> new CommentResponseDto(comment.getId(), comment.getText(), comment.getCreatedAt(), comment.getUpdatedAt()))
                .collect(Collectors.toList());
    }

    public CommentResponseDto updateComment(Long id, CommentUpdateRequestDto dto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));
        comment.setText(dto.getText());
        comment = commentRepository.save(comment);
        return new CommentResponseDto(comment.getId(), comment.getText(), comment.getCreatedAt(), comment.getUpdatedAt());
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));
        commentRepository.delete(comment);
    }



}
