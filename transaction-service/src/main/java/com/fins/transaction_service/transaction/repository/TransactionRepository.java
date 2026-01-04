package com.fins.transaction_service.transaction.repository;

import com.fins.transaction_service.transaction.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
    @Override
    Page<Transaction> findAll(Pageable pageable);

    Page<Transaction> findByTranAccIdAndTransactionDateBetweenOrderByTransactionDateAsc(String tranAccId, LocalDateTime from, LocalDateTime to, Pageable pageable);
}
