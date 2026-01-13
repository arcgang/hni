package com.aurumone.compliance.dto;

import com.aurumone.compliance.entity.AlertStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for reviewing/resolving alerts
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewAlertRequest {
    
    @NotNull(message = "Status is required")
    private AlertStatus status;
    
    @NotNull(message = "Reviewed by is required")
    private String reviewedBy;
    
    private String resolution;
}
