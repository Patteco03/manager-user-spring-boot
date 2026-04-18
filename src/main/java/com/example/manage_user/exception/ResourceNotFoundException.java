package com.example.manage_user.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends  RuntimeException {
    private final ErrorCode code;

    public ResourceNotFoundException(String message) {
        super(message);
        this.code = ErrorCode.USER_NOT_FOUND;
    }
}
