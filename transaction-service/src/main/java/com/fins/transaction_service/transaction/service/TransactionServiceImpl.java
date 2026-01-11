package com.fins.transaction_service.transaction.service;

import com.fins.transaction_service.tran_account.model.TranAccount;
import com.fins.transaction_service.tran_account.repository.TranAccountRepository;
import com.fins.transaction_service.transaction.dtos.TransactionReq;
import com.fins.transaction_service.transaction.dtos.TransactionRes;
import com.fins.transaction_service.transaction.mapper.TransactionMapper;
import com.fins.transaction_service.transaction.model.Transaction;
import com.fins.transaction_service.transaction.model.TransactionType;
import com.fins.transaction_service.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    private final TranAccountRepository tranAccountRepository;

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

//    private final CategoryValidationService categoryValidationService;

    @Override
    public TransactionRes createTransaction(TransactionReq req) {
//        boolean isValidTranAccId = tranAccountService.existsByTranAccountId(req.getTranAccId());
//        boolean isValidCategoryId = categoryValidationService.validateCategory(req.getCategoryId());

        TranAccount tranAccount = tranAccountRepository.findById(req.getTranAccId())
                .orElseThrow(() -> new RuntimeException("Transaction Account not found: " + req.getTranAccId()));

        Transaction transaction = transactionMapper.toEntity(req);
        transaction.setCategory(req.getCategory().toLowerCase());
        Transaction saved = transactionRepository.save(transaction);

        updateTranAccountBalance(transaction, saved, tranAccount);

        //Publish to RabbitMQ for AI Processing
        try{
            rabbitTemplate.convertAndSend(exchange, routingKey, saved);
        }catch (Exception e){
            log.error("Failed to publish activity to RabbitMQ: ", e);
        }

        return transactionMapper.toResponse(saved);
    }

    @Override
    public TransactionRes updateTransaction(String id, TransactionReq req) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        transactionMapper.updateEntityFromRequest(req, transaction);

        Transaction updated = transactionRepository.save(transaction);
        return transactionMapper.toResponse(updated);
    }

    @Override
    public Page<TransactionRes> getAllTransactions(Pageable pageable) {
        return transactionRepository.findAll(pageable)
                .map(transactionMapper::toResponse);
    }

    @Override
    public Optional<TransactionRes> getTransactionById(String id) {
        return transactionRepository.findById(id)
                .map(transactionMapper::toResponse);
    }

    @Override
    public Page<TransactionRes> getTransactionBetween(String tranAccId, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return transactionRepository.findByTranAccIdAndTransactionDateBetweenOrderByTransactionDateAsc(tranAccId, from, to, pageable)
                .map(transactionMapper::toResponse);
    }

    private void updateTranAccountBalance(Transaction transaction, Transaction saved, TranAccount tranAccount){
        BigDecimal currentBalance = tranAccount.getBalance();
        BigDecimal transactionAmount = transaction.getAmount();

        BigDecimal updatedBalance = BigDecimal.ZERO;

        if(saved.getType() == TransactionType.DEBIT && transactionAmount.compareTo(currentBalance) <= 0){
            updatedBalance = currentBalance.subtract(transactionAmount);
            tranAccount.setBalance(updatedBalance);
        }else if (saved.getType() == TransactionType.CREDIT){
            updatedBalance = currentBalance.add(transactionAmount);
            tranAccount.setBalance(updatedBalance);
        }

        tranAccountRepository.save(tranAccount);
    }
}
