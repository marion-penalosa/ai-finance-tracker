package com.fins.transaction_service.transaction.service;

import com.fins.transaction_service.transaction.dtos.TransactionReq;
import com.fins.transaction_service.transaction.dtos.TransactionRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TransactionService {
    TransactionRes createTransaction(TransactionReq req);
    TransactionRes updateTransaction(String id, TransactionReq req);
    Page<TransactionRes> getAllTransactions(Pageable pageable);
    Optional<TransactionRes> getTransactionById(String id);
    Page<TransactionRes> getTransactionBetween(String tranAccId, LocalDateTime from, LocalDateTime to, Pageable pageable);
}
