package com.Sahil.inventory_management.Repository;

import com.Sahil.inventory_management.model.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {
    List<TransactionLog> findByProductId(Long productId);
}
