package com.Sahil.inventory_management.DTO;

import java.time.LocalDateTime;

public class BaseResponseDTO<T> {
    String status;
    String message;
    LocalDateTime timestamp;
    private T data;

    public BaseResponseDTO(String status, String message, T data, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    public static <T> BaseResponseDTO<T> success(String message, T data) {
        return new BaseResponseDTO<>("SUCCESS", message, data, LocalDateTime.now());
    }

    public static <T> BaseResponseDTO<T> error(String message) {
        return new BaseResponseDTO<>("ERROR", message, null, LocalDateTime.now());
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
