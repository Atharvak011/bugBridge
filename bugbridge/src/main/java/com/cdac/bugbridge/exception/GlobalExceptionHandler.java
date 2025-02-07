package com.cdac.bugbridge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cdac.bugbridge.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🎯 Handle validation errors (e.g., @Valid in DTOs)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
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
        ErrorResponse errorResponse = new ErrorResponse(400, errorMessage, "/api/users/register");
        return ResponseEntity.badRequest().body(errorResponse);
    }

    // 🎯 Handle user already exists exception
    @ExceptionHandler(UserException.UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserException.UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, ex.getMessage(), "/api/users/register"));
    }

    // 🎯 Handle general exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(500, "An unexpected error occurred: " + ex.getMessage(),
                "/api/users/register");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
