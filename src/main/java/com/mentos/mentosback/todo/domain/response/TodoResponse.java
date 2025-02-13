package com.mentos.mentosback.todo.domain.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TodoResponse {
    private Long todoId;
    private Long userId;
    private String todoContent;
    private String createdAt;
    private Boolean isCompleted;
    private String goalDate;
    private String categoryName;
}
