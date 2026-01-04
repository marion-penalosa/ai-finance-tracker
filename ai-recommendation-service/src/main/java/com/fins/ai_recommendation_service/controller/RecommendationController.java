package com.fins.ai_recommendation_service.controller;

import com.fins.ai_recommendation_service.dtos.AiRecommendationRes;
import com.fins.ai_recommendation_service.dtos.RecommendationReq;
import com.fins.ai_recommendation_service.dtos.TransactionRes;
import com.fins.ai_recommendation_service.service.RecommendationService;
import com.fins.ai_recommendation_service.service.TransactionFetchService;
import com.fins.ai_recommendation_service.service.TransactionPreprocessService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;
    private final TransactionFetchService transactionFetchService;
    private final TransactionPreprocessService transactionPreprocessService;


    @PostMapping()
    public ResponseEntity<RecommendationReq> generateRecommendation(
            @RequestParam String tranAccId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ){
//        AiRecommendationRes res = recommendationService.generateRecommendation(tranAccId, from, to);

        RecommendationReq res = recommendationService.aggregateTransactions(tranAccId, from, to);

        return ResponseEntity.ok(res);
    }


    @GetMapping
    public ResponseEntity<Page<AiRecommendationRes>> getAllRecommendations(Pageable pageable){
        return ResponseEntity.ok(recommendationService.getAllRecommendations(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AiRecommendationRes> getRecommendationById(@PathVariable String id){
        return recommendationService.getRecommendationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tranAccId/{tranAccId}")
    public ResponseEntity<Page<AiRecommendationRes>> getRecommendationsByTranAccId(
            @PathVariable String tranAccId,
            Pageable pageable){
        return ResponseEntity.ok(recommendationService.getRecommendationsByTranAccId(tranAccId, pageable));

    }

}
