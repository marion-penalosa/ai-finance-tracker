package com.fins.ai_recommendation_service.repository;

import com.fins.ai_recommendation_service.model.Recommendation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends MongoRepository<Recommendation, String> {
    List<Recommendation> findByTranAccId(String tranAccId);

    @Override
    Page<Recommendation> findAll(Pageable pageable);

    Page<Recommendation> findByTranAccId(String tranAccId, Pageable pageable);
}
