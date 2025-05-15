package com.intern.authsystem.presentation.dto.response;

import com.intern.authsystem.domain.constant.Role;


public record SignUpResponse( String email,
                              String nickname,
                            Role role) {
    public static SignUpResponse of(String email,
                                    String nickname,
                                    Role role) {
        return new SignUpResponse(email, nickname, role);
    }
}