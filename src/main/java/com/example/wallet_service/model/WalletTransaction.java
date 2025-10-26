package com.example.wallet_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wallet_transaction")
public class WalletTransaction {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "wallet_id", columnDefinition = "uuid", nullable = false)
    private UUID walletId;

    @Column(name = "operation_type", nullable = false)
    private String operationType;

    @Column(name = "amount", precision = 19, scale = 4, nullable = false)
    private BigDecimal amount;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();


    public WalletTransaction(UUID id, UUID walletId, String operationType, BigDecimal amount){
        this.id = id;
        this.walletId = walletId;
        this.operationType = operationType;
        this.amount = amount;
    }
}
