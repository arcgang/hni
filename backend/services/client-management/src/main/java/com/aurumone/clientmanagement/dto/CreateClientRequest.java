package com.aurumone.clientmanagement.dto;

import com.aurumone.domain.enums.ClientSegment;
import com.aurumone.domain.enums.KYCStatus;
import com.aurumone.domain.enums.RiskProfile;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Create Client Request DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientRequest {
    
    @NotNull(message = "First name is required")
    private String firstName;
    
    @NotNull(message = "Last name is required")
    private String lastName;
    
    @Email(message = "Valid email is required")
    @NotNull(message = "Email is required")
    private String email;
    
    private String phone;
    
    @NotNull(message = "Client segment is required")
    private ClientSegment segment;
    
    @NotNull(message = "Risk profile is required")
    private RiskProfile riskProfile;
    
    @NotNull(message = "Net worth value is required")
    private BigDecimal netWorthValue;
    
    @NotNull(message = "Currency is required")
    private String currency;
    
    private Boolean familyOffice;
    
    private String domicileCountry;
}
