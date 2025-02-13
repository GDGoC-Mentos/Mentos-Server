package com.mentos.mentosback.todo.domain.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TodoRequest {
    private Long userId;
    private String todoContent;
    private String goalDate;
    private Long categoryId;
}
