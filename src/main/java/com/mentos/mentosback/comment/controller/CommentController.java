package com.mentos.mentosback.comment.controller;

import com.mentos.mentosback.comment.domain.request.CommentRequest;
import com.mentos.mentosback.comment.domain.response.CommentResponse;
import com.mentos.mentosback.comment.service.CommentService;
import com.mentos.mentosback.common.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("")
    public ApiResponse<CommentResponse> createComment(@RequestBody CommentRequest commentRequest) {
        CommentResponse response = commentService.createComment(commentRequest);
        return ApiResponse.onSuccess(response);
    }

    @PatchMapping("/edit/{commentId}")
    public ApiResponse<CommentResponse> editComment(@RequestBody CommentRequest commentRequest, @PathVariable Long commentId) {
        CommentResponse response = commentService.editComment(commentRequest, commentId);
        return ApiResponse.onSuccess(response);
    }

    @DeleteMapping("/delete/{commentId}")
    public ApiResponse<CommentResponse> deleteComment(@PathVariable Long commentId) {
        CommentResponse response = commentService.deleteComment(commentId);
        return ApiResponse.onSuccess(response);
    }
    //댓글 보여주기 (매칭 아이디 기준으로 해야 되는게, 만약 같은 카테고리를 가진 유저가 여러명? 방을 어떻게 나눌건데? 매칭은 2명이니)

}
