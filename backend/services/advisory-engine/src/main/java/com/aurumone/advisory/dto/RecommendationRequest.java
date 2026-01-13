package com.aurumone.advisory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

/**
 * Recommendation Request DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationRequest {
    
    private UUID clientId;
    private UUID portfolioId;
    private String recommendationType; // REBALANCE, NEW_PRODUCT, RISK_ADJUSTMENT
    private Map<String, Object> context;
}
