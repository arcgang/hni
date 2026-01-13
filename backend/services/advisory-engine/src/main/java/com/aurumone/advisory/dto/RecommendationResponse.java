package com.aurumone.advisory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Recommendation Response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationResponse {
    
    private UUID recommendationId;
    private UUID clientId;
    private String recommendationType;
    private List<ProductRecommendation> recommendations;
    private String rationale;
    private String riskDisclosure;
    private Double confidenceScore;
    private Boolean suitabilityCheckPassed;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductRecommendation {
        private UUID productId;
        private String productName;
        private String action; // BUY, SELL, HOLD
        private String reasoning;
    }
}
