package com.Sahil.inventory_management.Exception;

import com.Sahil.inventory_management.DTO.BaseResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    //  Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseResponseDTO<?>> handleResourceNotFound(ResourceNotFoundException ex) {
        BaseResponseDTO<?> response = new BaseResponseDTO<>(
                "ERROR",
                "Resource not Found Exception : " + ex.getMessage(),
                null,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    //  Invalid Input
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<BaseResponseDTO<?>> handleInvalidInput(InvalidInputException ex) {
        BaseResponseDTO<?> response = new BaseResponseDTO<>(
                "ERROR",
                "Invalid Input Exception :  " + ex.getMessage(),
                null,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //  Authentication Failure
    @ExceptionHandler(AuthenticationFailureException.class)
    public ResponseEntity<BaseResponseDTO<?>> handleAuthenticationFailure(AuthenticationFailureException ex) {
        BaseResponseDTO<?> response = new BaseResponseDTO<>(
                "ERROR",
                "Authentication Failure Exception : " + ex.getMessage(),
                null,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    //  Access Denied (Spring Security)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponseDTO<?>> handleAccessDenied(AccessDeniedException ex) {
        BaseResponseDTO<?> response = new BaseResponseDTO<>(
                "ERROR",
                "Access Denied Exception :" +ex.getMessage(),
                null,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    //  Generic fallback for all unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponseDTO<?>> handleGeneralException(Exception ex) {
        BaseResponseDTO<?> response = new BaseResponseDTO<>(
                "ERROR",
                "Something went wrong: " + ex.getMessage(),
                null,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
