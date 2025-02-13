package com.mentos.mentosback.comment.domain.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentRequest {
    private Long matchingId;
    private String writerId;
    private String commentContent;
}
