package com.intern.authsystem.presentation.dto.response;

import com.intern.authsystem.domain.constant.Role;
import lombok.Builder;
import lombok.Getter;

public record SignUpResponse(String email,
                             String nickname,
                             Role role) {
    @Builder
    public static SignUpResponse of(String email,
                                    String nickname,
                                    Role role) {
        return SignUpResponse.builder()
                             .email(email)
                             .nickname(nickname)
                             .role(role)
                             .build();
    }
}
