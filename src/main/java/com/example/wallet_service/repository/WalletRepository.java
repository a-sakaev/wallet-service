package com.example.wallet_service.repository;

import com.example.wallet_service.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    @Modifying
    @Query(value = "UPDATE wallet SET balance = balance + :amount, updated_at = now() WHERE id = :id", nativeQuery = true)
    int deposit(@Param("id") UUID id, @Param("amount") BigDecimal amount);

    @Modifying
    @Query(value = "UPDATE wallet SET balance = balance - :amount, updated_at = now() WHERE id = :id AND balance >= :amount", nativeQuery = true)
    int withdrawIfEnough(@Param("id") UUID id, @Param("amount") BigDecimal amount);
}
