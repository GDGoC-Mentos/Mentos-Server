package com.mentos.mentosback.users.service;

import com.mentos.mentosback.common.EmailAlreadyExistsException;
import com.mentos.mentosback.users.dto.UserSignupRequestDto;
import com.mentos.mentosback.users.entity.Status;
import com.mentos.mentosback.users.entity.User;
import com.mentos.mentosback.users.repository.UserRepository;
import com.mentos.mentosback.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void signup(UserSignupRequestDto request) {
        // 1. 이메일 중복 검사
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("이미 사용 중인 이메일입니다.");
        }

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 3. 회원 정보 저장
        User user = User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .name(request.getName())
                .role(request.getRole())
                .status(Status.PENDING) // 이메일 인증 전까지 PENDING 상태
                .build();
        userRepository.save(user);

        // 4. 이메일 인증을 위한 JWT 토큰 생성
        String token = jwtTokenProvider.generateToken(request.getEmail());

        // 5. 인증 이메일 발송
        String verificationUrl = "http://localhost:8080/users/verify-email?token=" + token;
        try {
            mailService.sendEmail(request.getEmail(), "이메일 인증", "멘토스 이메일 인증을 하려면 해당 링크를 클릭하세요: <br>" + verificationUrl);
        } catch (RuntimeException e) {
            throw new RuntimeException("회원가입 중 이메일 전송에 실패했습니다. 다시 시도해주세요.");
        }

    }
}
