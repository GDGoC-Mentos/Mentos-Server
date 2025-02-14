package com.mentos.mentosback.userinterest;


import com.mentos.mentosback.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {

    void deleteByUser(User user);
    List<UserInterest> findByUser(User user);
    Optional<UserInterest> findByUserAndCategoryId(User user, Long categoryId);
}
