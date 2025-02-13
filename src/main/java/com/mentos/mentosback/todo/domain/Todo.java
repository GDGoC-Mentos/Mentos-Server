package com.mentos.mentosback.todo.domain;

import com.mentos.mentosback.category.Category;
import com.mentos.mentosback.users.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "todo")
public class Todo {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "todo_content", nullable = false, length = 255)
    private String todoContent;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted;

    @Column(name = "goal_date", nullable = false)
    private LocalDate goalDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Builder
    private Todo(Long id, String todoContent, LocalDateTime createdAt, boolean isCompleted, LocalDate goalDate, User user, Category category) {
        this.id = id;
        this.todoContent = todoContent;
        this.createdAt = createdAt;
        this.isCompleted = isCompleted;
        this.goalDate = goalDate;
        this.user = user;
        this.category = category;
    }
}
