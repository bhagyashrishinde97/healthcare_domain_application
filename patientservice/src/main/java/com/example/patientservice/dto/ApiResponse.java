package com.example.patientservice.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Data
@Slf4j
public class ApiResponse <T>{
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    // ---------- SUCCESS RESPONSE WITH MESSAGE + DATA ----------
    public static <T> ApiResponse<T> success(String message, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        response.setTimestamp(LocalDateTime.now());
        return response;
    }

    // ---------- SUCCESS RESPONSE WITH ONLY DATA ----------
    public static <T> ApiResponse<T> success(T data) {
        return success("Operation successful", data);
    }

    // ---------- SUCCESS WITHOUT DATA ----------
    public static <T> ApiResponse<T> successMessage(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(null);
        response.setTimestamp(LocalDateTime.now());
        return response;
    }

    // ---------- ERROR RESPONSE ----------
    public static <T> ApiResponse<T> error(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setData(null);
        response.setTimestamp(LocalDateTime.now());
        return response;
    }
}