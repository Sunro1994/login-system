package com.intern.authsystem.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record LoginRequest(
        @NotBlank(message = "이메일은 필수 값입니다.") @Email(message = "유효한 이메일 형식이 아닙니다.") String email,
        @NotBlank(message = "비밀번호는 필수값입니다.") @Length(min = 6,message = "비밀번호는 최소 6자리 이상입니다") String password
) {
}
