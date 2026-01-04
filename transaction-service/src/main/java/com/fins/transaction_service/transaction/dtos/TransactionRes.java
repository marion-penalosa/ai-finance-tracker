package com.fins.transaction_service.transaction.dtos;

import com.fins.transaction_service.transaction.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRes {
    private String id;
    private BigDecimal amount;
    private String description;
    private String tranAccId;
    private TransactionType type;
    private String category;
    private String currency;
    private LocalDateTime transactionDate;
}
