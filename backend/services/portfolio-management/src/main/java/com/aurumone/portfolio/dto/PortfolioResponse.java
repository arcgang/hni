package com.aurumone.portfolio.dto;

import com.aurumone.domain.enums.AssetClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioResponse {
    
    private UUID portfolioId;
    private UUID clientId;
    private String portfolioName;
    private BigDecimal totalValue;
    private String currency;
}
