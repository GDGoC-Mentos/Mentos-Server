package com.mentos.mentosback.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ContextConfiguration
@ComponentScan
@TestPropertySource(locations = "classpath:application.yml") // application.yml을 명시적으로 로드
public class ApplicationYamlLoadTest {

    @Value("${jwt.secret:NOT_FOUND}")
    private String jwtSecret;

    @Value("${jwt.expiration:-1}")
    private long jwtExpiration;

    @Test
    void applicationYamlShouldLoad() {
        System.out.println("Loaded JWT Secret: " + jwtSecret);
        System.out.println("Loaded JWT Expiration: " + jwtExpiration);

        assertThat(jwtSecret).isNotEqualTo("NOT_FOUND"); // jwt.secret이 정상적으로 로드되는지 확인
        assertThat(jwtExpiration).isGreaterThan(0); // jwt.expiration이 정상적으로 로드되는지 확인
    }
}
