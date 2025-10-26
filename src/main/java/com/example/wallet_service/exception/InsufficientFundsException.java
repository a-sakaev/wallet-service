package com.example.wallet_service.exception;

import java.util.UUID;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(UUID id) {
        super("Недостаточно средств на кошельке: " + id);
    }
}
