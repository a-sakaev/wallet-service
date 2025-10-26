package com.example.wallet_service.controller;

import com.example.wallet_service.dto.WalletBalanceResponse;
import com.example.wallet_service.dto.WalletOperationRequest;
import com.example.wallet_service.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class WalletController {

    @Autowired
    private final WalletService service;

    @PostMapping("/wallet")
    public ResponseEntity<Void> operate(@Valid @RequestBody WalletOperationRequest request) {
        service.processOperation(request.getWalletId(), request.getOperationType(), request.getAmount());
        return ResponseEntity.accepted().build();
    }

    @GetMapping("wallet/{id}")
    public ResponseEntity<WalletBalanceResponse> getBalance(@PathVariable("id") UUID id){
        return ResponseEntity.ok(new WalletBalanceResponse(id, service.getBalance(id)));
    }

    //Создание кошелька для тестов
    @PostMapping("/wallet/create")
    public ResponseEntity<UUID> createWallet(@RequestParam(required = false) BigDecimal balance){
        if (balance == null){
            balance = BigDecimal.ZERO;
        }
        UUID walletId = service.createWallet(balance);
        return ResponseEntity.ok(walletId);
    }

}
