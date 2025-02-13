package com.mentos.mentosback.comment.domain.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponse {
    private Long commentId;
    private Long userId;
    private String InterestCategory;
    private String commentContent;
    private LocalDateTime createdAt;
}
