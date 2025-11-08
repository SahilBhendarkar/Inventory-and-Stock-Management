package com.Sahil.inventory_management.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "transaction_logs")
public class TransactionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private Long userId; // user who made the change
    private String changeType; // e.g., "INCREASE", "DECREASE"
    private Integer quantityChanged;
    private LocalDateTime createdAt;


    public TransactionLog() {
    }

    public TransactionLog(Long productId, Long userId, String changeType, Integer quantityChanged, LocalDateTime createdAt) {
        this.productId = productId;
        this.userId = userId;
        this.changeType = changeType;
        this.quantityChanged = quantityChanged;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public Integer getQuantityChanged() {
        return quantityChanged;
    }

    public void setQuantityChanged(Integer quantityChanged) {
        this.quantityChanged = quantityChanged;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
