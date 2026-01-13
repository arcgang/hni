package com.aurumone.compliance.dto;

import com.aurumone.compliance.entity.AlertSeverity;
import com.aurumone.compliance.entity.AlertType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Request DTO for creating compliance alerts
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAlertRequest {
    
    @NotNull(message = "Client ID is required")
    private UUID clientId;
    
    @NotNull(message = "Alert type is required")
    private AlertType alertType;
    
    @NotNull(message = "Severity is required")
    private AlertSeverity severity;
    
    @NotNull(message = "Description is required")
    private String description;
    
    private String details;
    
    private UUID transactionId;
    
    private UUID recommendationId;
}
