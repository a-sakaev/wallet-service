package com.example.wallet_service.service;

import com.example.wallet_service.dto.OperationType;
import com.example.wallet_service.exception.InsufficientFundsException;
import com.example.wallet_service.exception.WalletNotFoundException;
import com.example.wallet_service.repository.WalletRepository;
import com.example.wallet_service.repository.WalletTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private WalletTransactionRepository transactionRepository;

    @InjectMocks
    private WalletService service;

    private UUID walletId;

    @BeforeEach
    void init(){
        walletId = UUID.randomUUID();
    }

    @Test
    public void deposit_shouldIncrease(){
        when(walletRepository.deposit(walletId, BigDecimal.valueOf(1000)))
                .thenReturn(1);

        service.processOperation(walletId, OperationType.DEPOSIT,
                BigDecimal.valueOf(1000));

        verify(walletRepository, times(1)).deposit(walletId, BigDecimal.valueOf(1000));
        verify(transactionRepository, times(1)).save(any());
    }

    @Test
    public void withdraw_shouldDecreaseBalance_WhenEnoughFounds(){
        when(walletRepository.withdrawIfEnough(walletId, BigDecimal.valueOf(1000)))
                .thenReturn(1);
        service.processOperation(walletId, OperationType.WITHDRAW, BigDecimal.valueOf(1000));

        verify(walletRepository, times(1)).withdrawIfEnough(walletId, BigDecimal.valueOf(1000));
        verify(transactionRepository, times(1)).save(any());
    }

    @Test
    public void withdraw_shouldThrowException_whenNotEnoughFunds(){
        when(walletRepository.withdrawIfEnough(walletId, BigDecimal.valueOf(1000)))
                .thenReturn(0);
        when(walletRepository.existsById(walletId)).thenReturn(true);

        assertThrows(InsufficientFundsException.class, ()->
                service.processOperation(walletId, OperationType.WITHDRAW, BigDecimal.valueOf(1000)));
    }

    @Test
    public void withdraw_shouldThrowException_whenWalletNotFound(){
        when(walletRepository.withdrawIfEnough(walletId, BigDecimal.valueOf(1000)))
                .thenReturn(0);
        when(walletRepository.existsById(walletId)).thenReturn(false);

        assertThrows(WalletNotFoundException.class, ()->
                service.processOperation(walletId, OperationType.WITHDRAW, BigDecimal.valueOf(1000)));
    }


}
