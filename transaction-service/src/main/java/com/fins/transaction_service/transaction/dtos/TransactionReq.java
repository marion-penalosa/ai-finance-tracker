package com.fins.transaction_service.transaction.dtos;

import com.fins.transaction_service.transaction.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionReq {
    private BigDecimal amount;
    private String description;
    private String tranAccId;
    private TransactionType type;
    private String category;
    private String currency;
    private LocalDateTime transactionDate;
}
