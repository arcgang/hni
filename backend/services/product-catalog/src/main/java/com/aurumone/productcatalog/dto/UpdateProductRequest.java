package com.aurumone.productcatalog.dto;

import com.aurumone.domain.enums.ProductType;
import com.aurumone.domain.enums.RiskProfile;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Update Product Request DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {
    
    private String productName;
    
    private ProductType productType;
    
    private String description;
    
    @Min(value = 1, message = "Risk rating must be between 1 and 10")
    @Max(value = 10, message = "Risk rating must be between 1 and 10")
    private Integer riskRating;
    
    private RiskProfile minRiskProfile;
    
    private BigDecimal minInvestment;
    
    private String currency;
    
    private String issuer;
    
    private Boolean isActive;
}
