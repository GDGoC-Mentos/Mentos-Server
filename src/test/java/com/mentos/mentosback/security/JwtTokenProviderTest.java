package com.mentos.mentosback.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.secret:NOT_FOUND}")
    private String secretKey;

    @Test
    void shouldLoadJwtSecretFromYaml() {
        System.out.println("JWT Secret from application.yml: " + secretKey);
        assertThat(secretKey).isNotEqualTo("NOT_FOUND"); // 제대로 로드되었는지 확인
    }

    @Test
    void shouldGenerateJwtToken() {
        String token = jwtTokenProvider.generateToken("test@example.com");

        System.out.println("Generated JWT Token: " + token);
        assertThat(token).isNotEmpty(); // 토큰이 정상적으로 생성되었는지 확인
    }


}

