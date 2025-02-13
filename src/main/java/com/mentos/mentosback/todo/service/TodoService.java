package com.mentos.mentosback.todo.service;

import com.mentos.mentosback.category.Category;
import com.mentos.mentosback.category.CategoryService;
import com.mentos.mentosback.common.apiPayload.code.status.ErrorStatus;
import com.mentos.mentosback.common.apiPayload.exception.GeneralException;
import com.mentos.mentosback.todo.domain.Todo;
import com.mentos.mentosback.todo.domain.request.DayListRequest;
import com.mentos.mentosback.todo.domain.request.TodoRequest;
import com.mentos.mentosback.todo.domain.response.TodoResponse;
import com.mentos.mentosback.todo.repository.TodoRepository;
import com.mentos.mentosback.users.entity.User;
import com.mentos.mentosback.users.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    @Transactional
    public TodoResponse createTodo(TodoRequest todoRequest) {
        //user search
        User user = userService.findById(todoRequest.getUserId());
        //category search
        Category category= categoryService.findById(todoRequest.getCategoryId());
        //todo create
        Todo todo = Todo.builder()
                .todoContent(todoRequest.getTodoContent())
                .createdAt(LocalDateTime.now())
                .isCompleted(false)
                .goalDate(LocalDate.parse(todoRequest.getGoalDate()))
                //여기 수정 우선 user null 로 설정
//                .userId(todoRequest.getUserId())
//                .categoryName(todoRequest.getCategoryName()) //이걸 보내줄 때 id 로 보내는게 좋나?
                .build();
        //todo save
        todo = todoRepository.save(todo);
        //todo response
        return TodoResponse.builder()
                .todoId(todo.getId())
                .userId(user.getId())
                .todoContent(todo.getTodoContent())
                .createdAt(todo.getCreatedAt().toString())
                .isCompleted(false)
                .goalDate(todo.getGoalDate().toString())
                .categoryName(category.getName())
                .build();
    }

    @Transactional
    public TodoResponse editTodo(TodoRequest todoRequest, Long todoId) {
        //todo search
        Todo existingTodo = todoRepository.findById(todoId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ID_NOT_FOUND));
        //todo edit
        Todo updatedTodo = Todo.builder()
                .id(existingTodo.getId())
                .user(existingTodo.getUser())
                .category(existingTodo.getCategory())
                .todoContent(todoRequest.getTodoContent())
                .goalDate(LocalDate.parse(todoRequest.getGoalDate()))
                .isCompleted(existingTodo.isCompleted())
                .createdAt(existingTodo.getCreatedAt())
                .build();
        //todo save
        updatedTodo = todoRepository.save(updatedTodo);
        //todo response
        return TodoResponse.builder()
                .todoId(updatedTodo.getId())
                .userId(updatedTodo.getUser().getId())
                .todoContent(updatedTodo.getTodoContent())
                .createdAt(updatedTodo.getCreatedAt().toString())
                .isCompleted(updatedTodo.isCompleted())
                .goalDate(updatedTodo.getGoalDate().toString())
                .categoryName(updatedTodo.getCategory().getName())
                .build();
    }

    @Transactional
    public TodoResponse completeTodo(Long todoId) {
        Todo completedTodo;
        //todo search
        Todo existingTodo = todoRepository.findById(todoId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ID_NOT_FOUND));
        //todo complete 완료가 됐다고 잘못 누르면 그에 맞게 체크를 눌렀을 때 다시 false로 바꿔주기
        if (existingTodo.isCompleted() == true) {
            completedTodo = Todo.builder()
                    .id(existingTodo.getId())
                    .todoContent(existingTodo.getTodoContent())
                    .goalDate(existingTodo.getGoalDate())
                    .isCompleted(false)
                    .createdAt(existingTodo.getCreatedAt())
                    .category(existingTodo.getCategory())
                    .user(existingTodo.getUser())
                    .build();
        } else {
            completedTodo = Todo.builder()
                    .id(existingTodo.getId())
                    .todoContent(existingTodo.getTodoContent())
                    .goalDate(existingTodo.getGoalDate())
                    .isCompleted(true)
                    .createdAt(existingTodo.getCreatedAt())
                    .category(existingTodo.getCategory())
                    .user(existingTodo.getUser())
                    .build();
        }
        //todo save
        completedTodo = todoRepository.save(completedTodo);
        //todo response
        return TodoResponse.builder()
                .todoId(existingTodo.getId())
                .userId(completedTodo.getId())
                .todoContent(completedTodo.getTodoContent())
                .createdAt(completedTodo.getCreatedAt().toString())
                .isCompleted(completedTodo.isCompleted())
                .goalDate(completedTodo.getGoalDate().toString())
                .categoryName(completedTodo.getCategory().getName())
                .build();
    }

    @Transactional
    public TodoResponse deleteTodo(Long todoId) {
        //todo search
        Todo existingTodo = todoRepository.findById(todoId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ID_NOT_FOUND));
        //todo delete
        todoRepository.delete(existingTodo);
        //todo response
        return TodoResponse.builder()
                .todoId(existingTodo.getId())
                .userId(existingTodo.getUser().getId())
                .todoContent(existingTodo.getTodoContent())
                .createdAt(existingTodo.getCreatedAt().toString())
                .isCompleted(existingTodo.isCompleted())
                .goalDate(existingTodo.getGoalDate().toString())
                .categoryName(existingTodo.getCategory().getName())
                .build();
    }

    public List<TodoResponse> getTodoList(DayListRequest dayListRequest) {
//        //todo search (user, goalDate, category)
        List<Todo> todoList = todoRepository.findAllByUserIdAndCategoryAndGoalDate(
                dayListRequest.getUserId(),
                dayListRequest.getCategoryId(),
                LocalDate.parse(dayListRequest.getGoalDate()) // String을 날짜로 변환
        );
//        //todo response
        return todoList.stream()
                .map(todo -> TodoResponse.builder()
                        .todoId(todo.getId())
                        .userId(todo.getUser().getId())
                        .todoContent(todo.getTodoContent())
                        .createdAt(todo.getCreatedAt().toString())
                        .isCompleted(todo.isCompleted())
                        .goalDate(todo.getGoalDate().toString())
                        .categoryName(todo.getCategory().getName())
                        .build())
                .collect(Collectors.toList());
    }
}
