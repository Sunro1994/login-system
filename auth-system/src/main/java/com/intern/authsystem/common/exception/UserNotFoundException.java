package com.intern.authsystem.common.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("사용자를 찾을 수 없습니다. ID: " + userId);
    }
}