package com.example.wallet_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ApiError {
    private String code;
    private String message;
    private Instant timestamp = Instant.now();

    public ApiError(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
