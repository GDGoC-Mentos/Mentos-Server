package com.mentos.mentosback.todo.repository;

import com.mentos.mentosback.todo.domain.Todo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByUserIdAndCategoryAndGoalDate(Long userId, Long categoryId, LocalDate parse);
}
