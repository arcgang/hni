package com.aurumone.compliance.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Request DTO for transaction compliance checks
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCheckRequest {
    
    @NotNull(message = "Client ID is required")
    private UUID clientId;
    
    @NotNull(message = "Transaction ID is required")
    private UUID transactionId;
    
    @NotNull(message = "Amount is required")
    private BigDecimal amount;
    
    @NotNull(message = "Currency is required")
    private String currency;
    
    private String transactionType;
    
    private String counterparty;
    
    private String jurisdiction;
}
