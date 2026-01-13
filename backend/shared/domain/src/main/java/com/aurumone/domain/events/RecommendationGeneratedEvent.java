package com.aurumone.domain.events;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Recommendation Generated Event
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Builder
public class RecommendationGeneratedEvent extends DomainEvent {
    
    private UUID recommendationId;
    private UUID clientId;
    private UUID productId;
    private String recommendationType;
    private String rationale;
    private Double confidenceScore;
    
    public RecommendationGeneratedEvent(UUID recommendationId, UUID clientId, 
                                       UUID productId, String recommendationType,
                                       String rationale, Double confidenceScore) {
        super("RecommendationGenerated", recommendationId.toString());
        this.recommendationId = recommendationId;
        this.clientId = clientId;
        this.productId = productId;
        this.recommendationType = recommendationType;
        this.rationale = rationale;
        this.confidenceScore = confidenceScore;
    }
}
