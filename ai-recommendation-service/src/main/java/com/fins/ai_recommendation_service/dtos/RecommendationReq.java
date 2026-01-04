package com.fins.ai_recommendation_service.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationReq {
    private String tranAccId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fromDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime toDate;

    private BigDecimal totalSpent;
    private Map<String, BigDecimal> transactionsByCategory;
    private Map<String, Long> countByCategory;

    private BigDecimal avgSpent;
    private Map<String, BigDecimal> avgSpentByCategory;
    private TransactionRes mostSpentTransaction;
    private Map<String, TransactionRes> topTransactionByCategory;
}
