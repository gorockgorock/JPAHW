package com.sparta.travelnewsfeed.service;

import com.sparta.travelnewsfeed.dto.request.CommentRequestDTO;
import com.sparta.travelnewsfeed.entity.Comment;
import com.sparta.travelnewsfeed.entity.User;
import com.sparta.travelnewsfeed.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    public Comment createComment(CommentRequestDTO requestDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Comment comment = new Comment();
        comment.setText(requestDTO.getText());
        comment.setUser(userService.findByUsername(username));
        comment.setPost(postService.findById(requestDTO.getPostId()));
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        
        return commentRepository.save(comment);
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Transactional
    public Comment updateComment(Long id, CommentRequestDTO requestDTO) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("잘못된 비밀번호입니다. 비밀번호를 다시 입력해주세요.");
        }

        comment.setText(requestDTO.getText());
        commentRepository.save(comment);

        return comment;
    }

    @Transactional
    public void deleteComment(Long id) {
        Comment comment = getCommentById(id);
        commentRepository.delete(comment);
    }
}
