package com.aurumone.productcatalog.dto;

import com.aurumone.domain.enums.ProductType;
import com.aurumone.domain.enums.RiskProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Product Response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    
    private UUID productId;
    private String productName;
    private String productCode;
    private ProductType productType;
    private String description;
    private Integer riskRating;
    private RiskProfile minRiskProfile;
    private BigDecimal minInvestment;
    private String currency;
    private String issuer;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
