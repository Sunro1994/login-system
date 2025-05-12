package com.intern.authsystem.common.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String username) {
        super("이미 가입된 사용자입니다.");
    }
}