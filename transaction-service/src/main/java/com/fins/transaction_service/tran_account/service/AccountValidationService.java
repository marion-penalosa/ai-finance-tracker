package com.fins.transaction_service.tran_account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
public class AccountValidationService {

    private final WebClient userServiceWebClient;
    public boolean validateAccount(String accountId){
        try{
            return Boolean.TRUE.equals(userServiceWebClient.get()
                    .uri("/api/accounts/{accountId}/validate", accountId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block());

        }catch (WebClientResponseException e){
            if (e.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new RuntimeException("Account not found: " + accountId);
            }
            else if (e.getStatusCode() == HttpStatus.BAD_REQUEST){
                throw new RuntimeException("Invalid Request: " + accountId);
            }
        }
        return false;
    }
}
