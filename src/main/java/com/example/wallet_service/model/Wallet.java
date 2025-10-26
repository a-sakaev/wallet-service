package com.example.wallet_service.model;

import jakarta.persistence.*;
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
@Table(name = "wallet", schema = "public")
public class Wallet {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    public UUID id;

    @Column(name = "balance", precision = 19, scale = 4, nullable = false)
    public BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = OffsetDateTime.now();
    }

}
