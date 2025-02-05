package com.cdac.bugbridge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cdac.bugbridge.response.UserApiResponse;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🎯 Handle validation errors (e.g., @Valid in DTOs)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<UserApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // // Get the first field error
        // List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        // String errorMessage = "Invalid input"; // Default message
        // if (!fieldErrors.isEmpty()) {
        // errorMessage = fieldErrors.get(0).getDefaultMessage(); // Get the first
        // validation message
        // }

        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage) // Get only the validation message
                .findFirst()
                .orElse("Invalid input");
        UserApiResponse userApiResponse = new UserApiResponse(400, errorMessage, "/api/users/register");
        return ResponseEntity.badRequest().body(userApiResponse);
    }

    // 🎯 Handle user already exists exception
    @ExceptionHandler(UserException.UserAlreadyExistsException.class)
    public ResponseEntity<UserApiResponse> handleUserAlreadyExists(UserException.UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new UserApiResponse(400, ex.getMessage(), "/api/users/register", null, null));
    }

    // 🎯 Handle general exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", 500);
        response.put("message", "An unexpected error occurred: " + ex.getMessage());
        response.put("path", "/api/users/register");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
