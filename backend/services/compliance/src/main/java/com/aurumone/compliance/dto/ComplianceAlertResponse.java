package com.aurumone.compliance.dto;

import com.aurumone.compliance.entity.AlertSeverity;
import com.aurumone.compliance.entity.AlertStatus;
import com.aurumone.compliance.entity.AlertType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response DTO for compliance alerts
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceAlertResponse {
    private UUID id;
    private UUID clientId;
    private AlertType alertType;
    private AlertSeverity severity;
    private AlertStatus status;
    private String description;
    private String details;
    private UUID transactionId;
    private UUID recommendationId;
    private LocalDateTime createdAt;
    private LocalDateTime reviewedAt;
    private String reviewedBy;
    private String resolution;
}
