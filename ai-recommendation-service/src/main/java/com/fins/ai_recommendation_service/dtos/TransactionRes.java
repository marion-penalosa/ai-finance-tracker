package com.fins.ai_recommendation_service.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fins.ai_recommendation_service.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime transactionDate;
}
