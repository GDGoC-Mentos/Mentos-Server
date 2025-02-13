package com.mentos.mentosback.todo.controller;


import com.mentos.mentosback.common.apiPayload.ApiResponse;
import com.mentos.mentosback.todo.domain.request.DayListRequest;
import com.mentos.mentosback.todo.domain.request.TodoRequest;
import com.mentos.mentosback.todo.domain.response.TodoResponse;
import com.mentos.mentosback.todo.service.TodoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    @GetMapping("/list")
    public ApiResponse<List<TodoResponse>> getTodoList(@RequestBody DayListRequest dayListRequest) {
        List<TodoResponse> response = todoService.getTodoList(dayListRequest);
        return ApiResponse.onSuccess(response);
    }

    //할 일 등록
    @PostMapping("")
    public ApiResponse<TodoResponse> createTodo(@RequestBody TodoRequest todoRequest) {
        TodoResponse response = todoService.createTodo(todoRequest);
        return ApiResponse.onSuccess(response);
    }
    //할 일 수정
    @PatchMapping("/edit/{todoId}")
    public ApiResponse<TodoResponse> editTodo(@RequestBody TodoRequest todoRequest, @PathVariable Long todoId) {
        TodoResponse response = todoService.editTodo(todoRequest, todoId);
        return ApiResponse.onSuccess(response);
    }
    //할 일 완료 여부
    @PatchMapping("/complete/{todoId}")
    public ApiResponse<TodoResponse> completeTodo(@PathVariable Long todoId) {
        TodoResponse response = todoService.completeTodo(todoId);
        return ApiResponse.onSuccess(response);
    }
    //할 일 삭제
    @DeleteMapping("/delete/{todoId}")
    public ApiResponse<TodoResponse> deleteTodo(@PathVariable Long todoId) {
        TodoResponse response = todoService.deleteTodo(todoId);
        return ApiResponse.onSuccess(response);
    }
}
