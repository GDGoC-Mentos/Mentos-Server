package com.mentos.mentosback.users.controller;

import com.mentos.mentosback.security.JwtTokenProvider;
import com.mentos.mentosback.users.entity.Status;
import com.mentos.mentosback.users.entity.User;
import com.mentos.mentosback.users.repository.UserRepository;
import com.mentos.mentosback.users.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class EmailVerificationController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
        try {
            // 1️. 토큰에서 이메일 가져오기
            String email = jwtTokenProvider.getEmailFromToken(token);

            // 2️. DB에서 사용자 조회
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("해당 이메일의 사용자가 존재하지 않습니다."));

            // 3️. 이미 인증된 사용자라면 오류 반환
            if (user.getStatus() == Status.INCOMPLETE) {
                return ResponseEntity.badRequest().body(Map.of("message", "이미 인증된 사용자입니다."));
            }

            // 4️. 이메일 인증 완료 처리 (상태를 INCOMPLETE으로 변경) (추가 정보 입력 필요)
            user = user.withStatus(Status.INCOMPLETE);
            userRepository.save(user);

            return ResponseEntity.ok(Map.of("message", "이메일 인증 완료. 앱으로 돌아가 추가 정보를 입력해 주세요."));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "토큰이 만료되었습니다. 다시 인증 요청을 해주세요."));
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "유효하지 않은 토큰입니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "서버 오류가 발생했습니다."));
        }
    }
}
