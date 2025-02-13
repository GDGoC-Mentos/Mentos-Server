package com.mentos.mentosback.comment.service;

import com.mentos.mentosback.comment.domain.Comment;
import com.mentos.mentosback.comment.domain.request.CommentRequest;
import com.mentos.mentosback.comment.domain.response.CommentResponse;
import com.mentos.mentosback.comment.repository.CommentRepository;
import com.mentos.mentosback.common.apiPayload.code.status.ErrorStatus;
import com.mentos.mentosback.common.apiPayload.exception.GeneralException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentResponse createComment(CommentRequest commentRequest) {
        //matching id search -> not found exception
        //user search -> not found exception
        //create comment
        Comment comment = Comment.builder()
                .commentContent(commentRequest.getCommentContent())
                .createdAt(LocalDateTime.now())
                .build();
        //comment save
        Comment savedComment = commentRepository.save(comment);
        //return response
        return CommentResponse.builder()
                .commentId(savedComment.getId())
                //임의
                .userId(commentRequest.getMatchingId())
                .InterestCategory("임의")
                .commentContent(savedComment.getCommentContent())
                .createdAt(savedComment.getCreatedAt())
                .build();
    }


    public CommentResponse editComment(CommentRequest commentRequest, Long commentId) {
        //comment search -> not found exception
        Comment comment = commentRepository.findById(commentId).
                orElseThrow(() -> new GeneralException(ErrorStatus.ID_NOT_FOUND));
        //user search -> not found exception
        //category search -> not found exception
        Comment editedComment = Comment.builder()
                .id(comment.getId())
                .commentContent(commentRequest.getCommentContent())
                .createdAt(comment.getCreatedAt())
                .build();
        //comment save
        editedComment = commentRepository.save(editedComment);
        //return response
        return CommentResponse.builder()
                .commentId(editedComment.getId())
                //임의
                .userId(commentRequest.getMatchingId())
                .InterestCategory("임의")
                .commentContent(editedComment.getCommentContent())
                .createdAt(editedComment.getCreatedAt())
                .build();
    }

    public CommentResponse deleteComment(Long commentId) {
        //comment search -> not found exception
        Comment comment = commentRepository.findById(commentId).
                orElseThrow(() -> new GeneralException(ErrorStatus.ID_NOT_FOUND));
        //comment delete
        commentRepository.delete(comment);
        //return response
        return CommentResponse.builder()
                .commentId(comment.getId())
                //임의
                .userId(2L)
                .InterestCategory("임의")
                .commentContent(comment.getCommentContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
