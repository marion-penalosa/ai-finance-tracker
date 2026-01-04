package com.fins.ai_recommendation_service.service;

import com.fins.ai_recommendation_service.dtos.RecommendationReq;
import com.fins.ai_recommendation_service.dtos.TransactionRes;
import com.fins.ai_recommendation_service.model.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionPreprocessService {
    private final TransactionFetchService transactionFetchService;

    public RecommendationReq preprocessTransactions(String tranAccId, LocalDateTime from, LocalDateTime to){
        List<TransactionRes> transactions = transactionFetchService.getTransactions(tranAccId, from, to);

        BigDecimal totalSpent = transactions.stream()
                .filter(t -> t.getType() == TransactionType.DEBIT)
                .map(TransactionRes::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, BigDecimal> transactionsByCategory = transactions.stream()
                .collect(Collectors.groupingBy(
                        TransactionRes::getCategory,
                        Collectors.mapping(TransactionRes::getAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
                ));

        Map<String, Long> countByCategory = transactions.stream()
                .collect(Collectors.groupingBy(
                        TransactionRes::getCategory,
                        Collectors.counting()
                ));

        BigDecimal avgSpent = totalSpent.divide(new BigDecimal(transactions.size()), 2, RoundingMode.HALF_UP);

        Map<String, BigDecimal> avgSpentByCategory = transactionsByCategory.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().divide(new BigDecimal(countByCategory.get(e.getKey())), 2, RoundingMode.HALF_UP)));

        TransactionRes mostSpentTransaction = transactions.stream()
                .filter(t -> t.getType() == TransactionType.DEBIT)
                .max(Comparator.comparing(TransactionRes::getAmount))
                .orElse(null);

        Map<String, TransactionRes> topTransactionByCategory = transactions.stream()
                .collect(Collectors.groupingBy(TransactionRes::getCategory,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(TransactionRes::getAmount)),
                                Optional::get)));

        return RecommendationReq.builder()
                .tranAccId(tranAccId)
                .fromDate(from)
                .toDate(to)
                .totalSpent(totalSpent)
                .transactionsByCategory(transactionsByCategory)
                .countByCategory(countByCategory)
                .avgSpent(avgSpent)
                .avgSpentByCategory(avgSpentByCategory)
                .mostSpentTransaction(mostSpentTransaction)
                .topTransactionByCategory(topTransactionByCategory)
                .build();
    }


}
