package com.example.wallet_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Тип операции с кошельком")
public enum OperationType {
    DEPOSIT,
    WITHDRAW
}
