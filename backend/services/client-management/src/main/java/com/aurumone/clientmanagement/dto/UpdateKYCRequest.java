package com.aurumone.clientmanagement.dto;

import com.aurumone.domain.enums.KYCStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Update KYC Request DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateKYCRequest {
    
    @NotNull(message = "KYC status is required")
    private KYCStatus kycStatus;
    
    private LocalDateTime kycExpiryDate;
}
