package com.example.netflix.domain.user.controller;

import com.example.netflix.domain.user.dto.AuthDto;
import com.example.netflix.domain.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody @Valid AuthDto.SignUpRequest request) {
        authService.signUp(request);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDto.TokenResponse> login(@RequestBody @Valid AuthDto.LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}