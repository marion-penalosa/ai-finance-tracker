package com.fins.ai_recommendation_service.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fins.ai_recommendation_service.dtos.AiRecommendationRes;
import com.fins.ai_recommendation_service.dtos.RecommendationReq;
import com.fins.ai_recommendation_service.model.Recommendation;
import com.fins.ai_recommendation_service.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationService {
    private final AiAnalysisService aiAnalysisService;
    private final TransactionPreprocessService transactionPreprocessService;
    private final RecommendationRepository recommendationRepository;


    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public RecommendationReq aggregateTransactions(String tranAccId, LocalDateTime from, LocalDateTime to){
        RecommendationReq aggregatedTxn = transactionPreprocessService.preprocessTransactions(tranAccId, from, to);

        try{
            rabbitTemplate.convertAndSend(exchange, routingKey, aggregatedTxn);
        }catch(Exception e){
            log.error("Failed to publish preprocessed transaction to RabbitMQ: ", e);
        }

        return aggregatedTxn;
    }

    public Page<AiRecommendationRes> getAllRecommendations(Pageable pageable){
        return recommendationRepository.findAll(pageable)
                .map(this::toResponse);
    }

    public Optional<AiRecommendationRes> getRecommendationById(String id){
        return recommendationRepository.findById(id)
                .map(this::toResponse);
    }

    public Page<AiRecommendationRes> getRecommendationsByTranAccId(String tranAccId, Pageable pageable){
        return recommendationRepository.findByTranAccId(tranAccId, pageable)
                .map(this::toResponse);
    }

    private AiRecommendationRes toResponse(Recommendation entity) {
        return AiRecommendationRes.builder()
                .tranAccId(entity.getTranAccId())
                .from(entity.getFromDate())
                .to(entity.getToDate())
                .aiResponse(entity.getAiResponse())
                .createdAt(entity.getCreatedAt())
                .build();
    }


}
