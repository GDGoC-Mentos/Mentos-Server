package com.mentos.mentosback.users.controller;

import com.mentos.mentosback.users.dto.UserSignupRequestDto;
import com.mentos.mentosback.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserSignupRequestDto request) {
        userService.signup(request);
        return ResponseEntity.ok().body("{\"message\": \"회원가입 요청 성공. 인증 이메일을 확인해주세요.\"}");
    }
}

