package com.example.wallet_service.service;

import com.example.wallet_service.controller.WalletController;
import com.example.wallet_service.dto.OperationType;
import com.example.wallet_service.dto.WalletOperationRequest;
import com.example.wallet_service.exception.InsufficientFundsException;
import com.example.wallet_service.exception.WalletNotFoundException;
import com.example.wallet_service.model.Wallet;
import com.example.wallet_service.model.WalletTransaction;
import com.example.wallet_service.repository.WalletRepository;
import com.example.wallet_service.repository.WalletTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletTransactionRepository transactionRepository;

    @Transactional
    public void processOperation(UUID walletId, OperationType operationType, BigDecimal amount) {

        if (operationType == OperationType.DEPOSIT) {
            int updated = walletRepository.deposit(walletId, amount);
            if (updated == 0) {
                throw new WalletNotFoundException(walletId);
            }
            transactionRepository.save(new WalletTransaction(UUID.randomUUID(), walletId, "DEPOSIT", amount));
        } else if (operationType == OperationType.WITHDRAW) {
            int updated = walletRepository.withdrawIfEnough(walletId, amount);
            if (updated == 0) {
                if (!walletRepository.existsById(walletId)) {
                    throw new WalletNotFoundException(walletId);
                } else {
                    throw new InsufficientFundsException(walletId);
                }
            }
            transactionRepository.save(new WalletTransaction(UUID.randomUUID(), walletId, "WITHDRAW", amount));
        } else {
            throw new IllegalArgumentException("Неизвестная операция");
        }
    }

    @Transactional
    public BigDecimal getBalance(UUID walletId){
        return walletRepository.findById(walletId)
                .map(w -> w.getBalance())
                .orElseThrow(() -> new WalletNotFoundException(walletId));
    }

    //Метод создания кошелька для собственных тестов
    public UUID createWallet(BigDecimal balance){
        Wallet wallet = new Wallet();
        wallet.setId(UUID.randomUUID());
        wallet.setBalance(balance);
        walletRepository.save(wallet);
        return wallet.getId();
    }


    }
