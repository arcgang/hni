package com.aurumone.compliance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response DTO for compliance check results
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceCheckResponse {
    private boolean approved;
    private String status;
    private List<String> violations;
    private List<String> warnings;
    private String reason;
}
