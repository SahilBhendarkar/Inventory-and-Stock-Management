package com.Sahil.inventory_management.Controllers;

import com.Sahil.inventory_management.Repository.TransactionLogRepository;
import com.Sahil.inventory_management.model.TransactionLog;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionLogController {

    private final TransactionLogRepository transactionLogRepository;

    public TransactionLogController(TransactionLogRepository transactionLogRepository) {
        this.transactionLogRepository = transactionLogRepository;
    }


    //  Admin can view all transaction logs

    @PreAuthorize("hasAnyRole('ADMIN', 'DEALER')")
    @GetMapping("/all")
    public ResponseEntity<List<TransactionLog>> getAllLogs() {
        return ResponseEntity.ok(transactionLogRepository.findAll());
    }


    //  Admin or Dealer can view logs for a specific product

    @PreAuthorize("hasAnyRole('ADMIN', 'DEALER')")
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<TransactionLog>> getLogsByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(transactionLogRepository.findByProductId(productId));
    }


}
