package com.intern.authsystem.domain.repository;

import com.intern.authsystem.domain.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public interface UserRepository {
    boolean existsByUsername(@NotBlank(message = "이메일은 필수 값입니다.") @Email(message = "유효한 이메일 형식이 아닙니다.") String email);

    User save(User user);

    Optional<User> findByUsername(@NotBlank(message = "이메일은 필수 값입니다.") @Email(message = "유효한 이메일 형식이 아닙니다.") String email);

    Optional<User> findById(Long userId);
}
