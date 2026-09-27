package com.example.netflix.global.config;

import com.example.netflix.global.jwt.JwtAuthenticationFilter;
import com.example.netflix.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1. Swagger UI 및 기본 에러 경로 허용
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/error"
                        ).permitAll()

                        // 2. 정적 이미지 파일 조회 허용 (누구나 이미지 URL로 접근 가능)
                        .requestMatchers("/images/**").permitAll()

                        // 3. 인증(회원가입, 로그인) 허용
                        .requestMatchers("/api/auth/**").permitAll()

                        // 4. 콘텐츠 조회(GET)는 누구나 가능
                        .requestMatchers(HttpMethod.GET, "/api/contents/**").permitAll()

                        // 5. 콘텐츠 등록/수정/삭제는 ADMIN 권한만 가능
                        .requestMatchers(HttpMethod.POST, "/api/contents/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/contents/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/contents/**").hasAuthority("ROLE_ADMIN")

                        // 6. 이미지 업로드 및 마이리스트(찜)는 로그인된 사용자만 가능
                        .requestMatchers("/api/images/**").authenticated()
                        .requestMatchers("/api/wishlists/**").authenticated()

                        // 그 외 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}