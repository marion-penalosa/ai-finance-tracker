package com.fins.transaction_service.transaction.controller;

import com.fins.transaction_service.transaction.dtos.TransactionReq;
import com.fins.transaction_service.transaction.dtos.TransactionRes;
import com.fins.transaction_service.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionRes> createTransaction(@RequestBody TransactionReq request){
        TransactionRes response = transactionService.createTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<TransactionRes>> getAllTransactions(Pageable pageable) {
        return ResponseEntity.ok(transactionService.getAllTransactions(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionRes> getTransactionById(@PathVariable String id) {
        return transactionService.getTransactionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/date")
    public ResponseEntity<Page<TransactionRes>> getTransactionsByDateRange(
            @RequestParam String tranAccId,
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to,
            Pageable pageable
            ){
        Page<TransactionRes> transactions = transactionService.getTransactionBetween(tranAccId, from, to, pageable);
        return ResponseEntity.ok(transactions);
    }
}
