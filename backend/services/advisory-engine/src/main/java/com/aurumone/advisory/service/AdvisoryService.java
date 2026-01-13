package com.aurumone.advisory.service;

import com.aurumone.advisory.agent.AIAdvisoryAgent;
import com.aurumone.advisory.dto.RecommendationRequest;
import com.aurumone.advisory.dto.RecommendationResponse;
import com.aurumone.domain.events.RecommendationGeneratedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Advisory Service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AdvisoryService {
    
    private final AIAdvisoryAgent advisoryAgent;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    public RecommendationResponse generateRecommendation(RecommendationRequest request) {
        log.info("Processing recommendation request for client: {}", request.getClientId());
        
        // Generate recommendation using AI agent
        RecommendationResponse response = advisoryAgent.generateRecommendation(request);
        
        // Publish event
        RecommendationGeneratedEvent event = new RecommendationGeneratedEvent(
                response.getRecommendationId(),
                response.getClientId(),
                response.getRecommendations().isEmpty() ? null : 
                    response.getRecommendations().get(0).getProductId(),
                response.getRecommendationType(),
                response.getRationale(),
                response.getConfidenceScore()
        );
        kafkaTemplate.send("recommendation-events", event);
        
        log.info("Recommendation generated: {}", response.getRecommendationId());
        return response;
    }
}
