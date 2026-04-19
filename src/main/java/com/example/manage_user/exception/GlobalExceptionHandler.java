package com.example.manage_user.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {
        ApiError error = new ApiError(
                ex.getCode().name(),
                ex.getMessage(),
                404,
                request.getRequestURI()
        );

        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        ApiError error = new ApiError(
                ErrorCode.VALIDATION_ERROR.name(),
                message,
                422,
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusiness(
            BusinessException ex,
            HttpServletRequest request
    ) {
        ApiError error = new ApiError(
                ex.getCode().name(),
                ex.getMessage(),
                400,
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {

        ApiError error = new ApiError(
                ErrorCode.INTERNAL_ERROR.name(),
                "Unexpected error",
                500,
                request.getRequestURI()
        );

        return ResponseEntity.status(500).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ) {

        if (ex.getRequiredType() == UUID.class) {
            ApiError error = new ApiError(
                    "INVALID_UUID",
                    "Invalid UUID format",
                    400,
                    request.getRequestURI()
            );

            return ResponseEntity.badRequest().body(error);
        }

        return ResponseEntity.status(400).build();
    }
}
