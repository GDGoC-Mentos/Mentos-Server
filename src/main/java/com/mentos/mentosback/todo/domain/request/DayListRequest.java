package com.mentos.mentosback.todo.domain.request;

import lombok.Builder;
import lombok.Getter;
//사용자가 날짜별로 할 일 목록을 조회할 때 요청하는 데이터
@Getter
@Builder
public class DayListRequest {
    private Long userId;
    private Long categoryId;
    private String goalDate;
}
