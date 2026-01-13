package com.aurumone.productcatalog.dto;

import com.aurumone.domain.enums.ProductType;
import com.aurumone.domain.enums.RiskProfile;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Create Product Request DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    
    @NotNull(message = "Product name is required")
    private String productName;
    
    @NotNull(message = "Product code is required")
    private String productCode;
    
    @NotNull(message = "Product type is required")
    private ProductType productType;
    
    private String description;
    
    @NotNull(message = "Risk rating is required")
    @Min(value = 1, message = "Risk rating must be between 1 and 10")
    @Max(value = 10, message = "Risk rating must be between 1 and 10")
    private Integer riskRating;
    
    private RiskProfile minRiskProfile;
    
    @NotNull(message = "Minimum investment is required")
    private BigDecimal minInvestment;
    
    @NotNull(message = "Currency is required")
    private String currency;
    
    private String issuer;
}
