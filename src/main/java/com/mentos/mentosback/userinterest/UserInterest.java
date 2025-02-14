package com.mentos.mentosback.userinterest;

import com.mentos.mentosback.category.Category;

import com.mentos.mentosback.users.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_interests",
uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "category_id"})})
public class UserInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public UserInterest(Long id, User user, Category category) {
        this.id = id;
        this.user = user;
        this.category = category;
    }

    public static UserInterest createUserInterest(User user, Category category) {
        UserInterest userInterest = new UserInterest();
        userInterest.user = user;
        userInterest.category = category;
        return userInterest;
    }

}
