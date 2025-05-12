package com.intern.authsystem.common.exception;

import lombok.Builder;

public record ErrorResponse(ErrorDetail error) {

    @Builder
    public static ErrorResponse of(String code, String message) {
        return ErrorResponse.builder().code(code).message(message).build();
    }

    public record ErrorDetail(String code, String message) {}
}