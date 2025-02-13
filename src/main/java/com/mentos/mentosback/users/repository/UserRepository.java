package com.mentos.mentosback.users.repository;

import com.mentos.mentosback.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);  // 이메일 중복 검사
    Optional<User> findByEmail(String email);  // 이메일로 회원 조회
}

