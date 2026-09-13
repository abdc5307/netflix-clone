package com.example.netflix.domain.user.dto;

import com.example.netflix.domain.user.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthDto {

    @Getter
    @NoArgsConstructor
    public static class SignUpRequest {
        @Email @NotBlank
        private String email;
        @NotBlank
        private String password;
        @NotBlank
        private String nickname;
        private Role role; // null이면 기본 USER
    }

    @Getter
    @NoArgsConstructor
    public static class LoginRequest {
        @Email @NotBlank
        private String email;
        @NotBlank
        private String password;
    }

    @Getter
    @NoArgsConstructor
    public static class TokenResponse {
        private String accessToken;
        private String tokenType = "Bearer";

        public TokenResponse(String accessToken) {
            this.accessToken = accessToken;
        }
    }
}