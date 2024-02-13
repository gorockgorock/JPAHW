package com.sparta.travelnewsfeed.service;

import com.sparta.travelnewsfeed.dto.request.CommentRequestDTO;
import com.sparta.travelnewsfeed.dto.response.CommentResponseDTO;
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
import java.util.stream.Collectors;

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
    public CommentResponseDTO createComment(CommentRequestDTO requestDTO) {
        // 현재 인증된 사용자의 사용자이름을 검색합니다.
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // 댓글 객체를 생성하고 요청에 따라 해당 객체의 속성을 설정합니다.
        Comment comment = new Comment();
        comment.setText(requestDTO.getText());
        comment.setUser(userService.findByUsername(username));
        comment.setPost(postService.findById(requestDTO.getPostId()));
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        return convertToResponseDTO(savedComment);
    }

    public CommentResponseDTO getCommentById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        return convertToResponseDTO(comment);
    }

    public List<CommentResponseDTO> getAllComments() {
        return commentRepository.findAll().stream()
                .map(comment -> new CommentResponseDTO(comment.getId(), comment.getText(),
                        comment.getUser().getUsername(), comment.getPost().getId(),
                        comment.getCreatedAt(), comment.getUpdatedAt()))
                .collect(Collectors.toList());
    }

    @Transactional
    //리포지토리에서 id로 댓글을 가져오며 찾을 수 없다면 예외처리한다.
    public CommentResponseDTO updateComment(Long id, CommentRequestDTO requestDTO) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        //보안 컨텍스트를 통해서 인증된 사용자를 가져오는 역할을한다.
        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        //요청된 비밀번호와 사용자의 비밀번호를 비교하여 일치하지 않으면 런타임 예외를 보낸다.
        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("잘못된 비밀번호입니다. 비밀번호를 다시 입력해주세요.");
        }
        //비번이 일치하면 텍스트와 현재 시간을 가져와서 업데이트한다.
        comment.setText(requestDTO.getText());
        comment.setUpdatedAt(LocalDateTime.now());
        // 업데이트된 댓글을 다시 저장소에 저장후 반환한다.
        Comment updatedComment = commentRepository.save(comment);
        return convertToResponseDTO(updatedComment);
    }

    @Transactional
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        commentRepository.delete(comment);
    }

    private CommentResponseDTO convertToResponseDTO(Comment comment) {
        CommentResponseDTO responseDTO = new CommentResponseDTO();
        responseDTO.setId(comment.getId());
        responseDTO.setText(comment.getText());
        responseDTO.setUsername(comment.getUser().getUsername());
        responseDTO.setPostId(comment.getPost().getId());
        responseDTO.setCreatedAt(comment.getCreatedAt());
        responseDTO.setUpdatedAt(comment.getUpdatedAt());
        return responseDTO;
    }
}
