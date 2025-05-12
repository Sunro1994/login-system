package com.intern.authsystem.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record SignUpRequest(
        @NotBlank @Email String username,
        @NotBlank @Length(min = 6) String password,
        @NotBlank  String nickname
) {
}
