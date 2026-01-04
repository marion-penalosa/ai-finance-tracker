package com.fins.ai_recommendation_service.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fins.ai_recommendation_service.dtos.RecommendationReq;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AiAnalysisService {
    private final TransactionPreprocessService transactionPreprocessService;
    private final ChatModel chatModel;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);



    public String generateRecommendation(RecommendationReq req) {
        // Preprocess transactions into your request DTO
//        RecommendationReq req = transactionPreprocessService.preprocessTransactions(tranAccId, from, to);

        String prompt = buildPromptForTransactions(req);

        String response = chatModel.call(prompt);
        return response;
    }

//    public String generateRecommendation(String tranAccId, LocalDateTime from, LocalDateTime to) {
//        // Preprocess transactions into your request DTO
//        RecommendationReq req = transactionPreprocessService.preprocessTransactions(tranAccId, from, to);
//
//        String prompt = buildPromptForTransactions(req);
//
//        String response = chatModel.call(prompt);
//        return response;
//    }

    private String buildPromptForTransactions(RecommendationReq req){

        return String.format("""
                Analyze this transaction record summary and provide detailed recommendations in the following EXACT JSON format:
                {
                    "summary":{
                        "transactionPeriod" : {
                            "from" : "%s",
                            "to": "%s"
                        },
                        "totalSpent": %s,
                        "averageSpent": %s,
                        "mostSignificantTransaction":{
                            "category": "category",
                            "amount": "Amount here",
                            "description": "Description here"
                        }
                    },
                    "narrative": "Generate Narrative here",
                    "overallActionableTips":[
                        "Actionable Tip 1",
                        "Actionable Tip 2",
                        "Actionable Tip 3",
                        "Actionable Tip 4",
                        "Actionable Tip 5"
                    ],
                    "categoricalAnalysis":{
                        "category":{
                            "totalSpent": "Total spent on category here",
                            "transactionCount": "Number of transaction here",
                            "averageSpent": "Average Spent here",
                            "insight": "Insight here",
                            "actionableTip":"Actionable Tip here",
                        },
                    },
                    "goals":{
                        "monthlySavingTarget":"Monthly saving target here",
                        "spendingAlertThreshold":"Spending Threshold here"
                    }
                }
                
                ---Transaction Data--
                %s
                """,
                req.getFromDate(),
                req.getToDate(),
                req.getTotalSpent(),
                req.getAvgSpent(),
                req
        );
    }

}
