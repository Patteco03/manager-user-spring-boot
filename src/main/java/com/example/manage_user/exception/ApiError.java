package com.example.manage_user.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiError {
    private final String code;
    private final String message;
    private final int status;
    private final LocalDateTime timestamp;
    private final String path;

    public ApiError(String code, String message, int status, String path) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }
}
