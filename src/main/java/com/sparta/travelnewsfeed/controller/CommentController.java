package com.sparta.travelnewsfeed.controller;

import com.sparta.travelnewsfeed.dto.request.CommentCreateRequestDto;
import com.sparta.travelnewsfeed.dto.request.CommentUpdateRequestDto;
import com.sparta.travelnewsfeed.dto.response.CommentResponseDto;
import com.sparta.travelnewsfeed.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {


    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentCreateRequestDto dto) {
        CommentResponseDto createdComment = commentService.createComment(dto);
        return ResponseEntity.ok(createdComment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long id) {
        CommentResponseDto comment = commentService.getCommentById(id);
        return ResponseEntity.ok(comment);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getAllCommentsByPostId() {
        List<CommentResponseDto> comments = commentService.findAllComments();
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id,
                                                            @RequestBody CommentUpdateRequestDto dto,
                                                            @RequestParam("password") String password) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        CommentResponseDto updatedComment = commentService.updateComment(id, dto, username, password);
        return ResponseEntity.ok(updatedComment);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }



}
