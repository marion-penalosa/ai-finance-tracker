package com.fins.ai_recommendation_service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "ai_recommendations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recommendation {

    @Id
    private String id;

    private String tranAccId;

    //date range
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    private Map<String, Object> aiResponse;

    @CreatedDate
    private LocalDateTime createdAt;
}
