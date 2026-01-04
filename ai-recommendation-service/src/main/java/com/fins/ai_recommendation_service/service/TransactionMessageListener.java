package com.fins.ai_recommendation_service.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fins.ai_recommendation_service.dtos.RecommendationReq;
import com.fins.ai_recommendation_service.dtos.TransactionRes;
import com.fins.ai_recommendation_service.model.Recommendation;
import com.fins.ai_recommendation_service.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionMessageListener {
    private final RecommendationService recommendationService;
    private final AiAnalysisService aiAnalysisService;
    private final RecommendationRepository recommendationRepository;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "transaction.queue")
    public void processTransaction(TransactionRes transactionRes){
        log.info("Received transaction for processing: {}", transactionRes.getId());
    }

    @RabbitListener(queues = "transaction.queue")
    public void generateAiRecommendation(RecommendationReq req){
        log.info("Received preprocessed transactions: {}", req);
        String aiResponse = aiAnalysisService.generateRecommendation(req);

        Map<String, Object> parsedResponse;
        try{
            parsedResponse = objectMapper.readValue(
                    aiResponse,
                    new TypeReference<>() {}
            );
        }catch (Exception e){
            throw new RuntimeException("Failed to parse AI response", e);
        }

        Recommendation saved = recommendationRepository.save(
                Recommendation.builder()
                        .tranAccId(req.getTranAccId())
                        .fromDate(req.getFromDate())
                        .toDate(req.getToDate())
                        .aiResponse(parsedResponse)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
        log.info("Ai recommendation generated successfully: {}", saved.getId());
    }

}

