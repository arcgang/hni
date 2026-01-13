package com.aurumone.compliance.controller;

import com.aurumone.compliance.dto.ComplianceCheckResponse;
import com.aurumone.compliance.dto.TransactionCheckRequest;
import com.aurumone.compliance.service.ComplianceCheckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Compliance Checks
 */
@RestController
@RequestMapping("/api/v1/compliance/checks")
@RequiredArgsConstructor
@Tag(name = "Compliance Checks", description = "APIs for performing compliance checks")
public class ComplianceCheckController {
    
    private final ComplianceCheckService checkService;
    
    @PostMapping("/aml")
    @Operation(summary = "Perform AML check", description = "Performs Anti-Money Laundering check on a transaction")
    public ResponseEntity<ComplianceCheckResponse> performAMLCheck(
            @Valid @RequestBody TransactionCheckRequest request) {
        ComplianceCheckResponse response = checkService.performAMLCheck(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/kyc/{kycStatus}")
    @Operation(summary = "Perform KYC check", description = "Verifies KYC status")
    public ResponseEntity<ComplianceCheckResponse> performKYCCheck(
            @PathVariable String kycStatus) {
        ComplianceCheckResponse response = checkService.performKYCCheck(kycStatus);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/suitability")
    @Operation(summary = "Perform suitability check", description = "Checks investment suitability")
    public ResponseEntity<ComplianceCheckResponse> performSuitabilityCheck(
            @RequestParam String clientRiskProfile,
            @RequestParam String productRiskRating) {
        ComplianceCheckResponse response = checkService.performSuitabilityCheck(
                clientRiskProfile, productRiskRating);
        return ResponseEntity.ok(response);
    }
}
