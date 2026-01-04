package com.fins.transaction_service.transaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
public class CategoryValidationService {
    private final WebClient categoryServiceWebClient;

    public boolean validateCategory(String categoryId){
        try{
            return Boolean.TRUE.equals(categoryServiceWebClient.get()
                    .uri("/api/categories/{categoryId}/validate", categoryId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block()
            );
        }catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new RuntimeException("Category not found: " + categoryId);
            }else if(e.getStatusCode() == HttpStatus.BAD_REQUEST){
                throw new RuntimeException("Invalid Request: " + categoryId);
            }
        }
        return false;
    }
}
