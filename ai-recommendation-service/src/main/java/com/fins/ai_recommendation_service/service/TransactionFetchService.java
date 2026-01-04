package com.fins.ai_recommendation_service.service;

import com.fins.ai_recommendation_service.dtos.PageResponse;
import com.fins.ai_recommendation_service.dtos.TransactionRes;

import com.fins.ai_recommendation_service.model.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionFetchService {

    private final RestTemplate restTemplate;
    private final String TRANSACTION_SERVICE_URL = "http://localhost:8082/api/transactions/date";

    public List<TransactionRes> getTransactions(String tranAccId, LocalDateTime from, LocalDateTime to) {
        return fetchAllPages(TRANSACTION_SERVICE_URL, tranAccId, from, to, 100);
    }

    private List<TransactionRes> fetchAllPages(String baseUrl, String tranAccId, LocalDateTime from, LocalDateTime to, int pageSize) {
        List<TransactionRes> allItems = new ArrayList<>();
        int page = 0;

        while (true) {
            String url = UriComponentsBuilder.fromUriString(baseUrl)
                    .queryParam("tranAccId", tranAccId)
                    .queryParam("from", from.toString())
                    .queryParam("to", to.toString())
                    .queryParam("page", page)
                    .queryParam("size", pageSize)
                    .toUriString();

            ResponseEntity<PageResponse<TransactionRes>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<PageResponse<TransactionRes>>() {}
            );

            PageResponse<TransactionRes> response = responseEntity.getBody();

            if (response == null || response.getContent().isEmpty()) {
                break;
            }

            allItems.addAll(response.getContent());

            if (page >= response.getTotalPages() - 1) {
                break;
            }

            page++;
        }

        return allItems;
    }

}
