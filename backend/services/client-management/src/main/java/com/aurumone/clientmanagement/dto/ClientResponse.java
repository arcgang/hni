package com.aurumone.clientmanagement.dto;

import com.aurumone.domain.enums.ClientSegment;
import com.aurumone.domain.enums.KYCStatus;
import com.aurumone.domain.enums.RiskProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Client Response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponse {
    
    private UUID clientId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private ClientSegment segment;
    private RiskProfile riskProfile;
    private KYCStatus kycStatus;
    private BigDecimal netWorthValue;
    private String currency;
    private Boolean familyOffice;
    private String domicileCountry;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime kycExpiryDate;
}
