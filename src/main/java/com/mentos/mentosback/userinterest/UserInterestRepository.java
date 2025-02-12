package com.mentos.mentosback.userinterest;

import com.mentos.mentosback.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {

    void deleteByUser(User user);
    List<UserInterest> findByUser(User user);
}
