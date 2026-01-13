package com.aurumone.compliance.service;

import com.aurumone.compliance.dto.ComplianceCheckResponse;
import com.aurumone.compliance.dto.TransactionCheckRequest;
import com.aurumone.compliance.entity.AlertSeverity;
import com.aurumone.compliance.entity.AlertType;
import com.aurumone.compliance.dto.CreateAlertRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for performing compliance checks
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ComplianceCheckService {
    
    private final ComplianceAlertService alertService;
    
    private static final BigDecimal AML_THRESHOLD = new BigDecimal("100000");
    private static final List<String> HIGH_RISK_JURISDICTIONS = List.of("XX", "YY", "ZZ");
    
    /**
     * Perform AML check on a transaction
     */
    public ComplianceCheckResponse performAMLCheck(TransactionCheckRequest request) {
        log.info("Performing AML check for transaction: {}", request.getTransactionId());
        
        List<String> violations = new ArrayList<>();
        List<String> warnings = new ArrayList<>();
        
        // Check transaction amount threshold
        if (request.getAmount().compareTo(AML_THRESHOLD) > 0) {
            warnings.add("Transaction amount exceeds AML threshold");
            
            // Create alert for high-value transaction
            CreateAlertRequest alertRequest = CreateAlertRequest.builder()
                    .clientId(request.getClientId())
                    .alertType(AlertType.AML_SUSPICIOUS_TRANSACTION)
                    .severity(AlertSeverity.HIGH)
                    .description("High-value transaction detected: " + request.getAmount() + " " + request.getCurrency())
                    .details("Transaction ID: " + request.getTransactionId())
                    .transactionId(request.getTransactionId())
                    .build();
            
            alertService.createAlert(alertRequest);
        }
        
        // Check jurisdiction
        if (request.getJurisdiction() != null && 
            HIGH_RISK_JURISDICTIONS.contains(request.getJurisdiction())) {
            violations.add("Transaction involves high-risk jurisdiction: " + request.getJurisdiction());
            
            CreateAlertRequest alertRequest = CreateAlertRequest.builder()
                    .clientId(request.getClientId())
                    .alertType(AlertType.HIGH_RISK_JURISDICTION)
                    .severity(AlertSeverity.CRITICAL)
                    .description("Transaction with high-risk jurisdiction: " + request.getJurisdiction())
                    .transactionId(request.getTransactionId())
                    .build();
            
            alertService.createAlert(alertRequest);
        }
        
        boolean approved = violations.isEmpty();
        String status = approved ? "APPROVED" : "BLOCKED";
        
        return ComplianceCheckResponse.builder()
                .approved(approved)
                .status(status)
                .violations(violations)
                .warnings(warnings)
                .reason(approved ? "Transaction passed AML checks" : "Transaction failed AML checks")
                .build();
    }
    
    /**
     * Perform KYC verification check
     */
    public ComplianceCheckResponse performKYCCheck(String kycStatus) {
        log.info("Performing KYC check with status: {}", kycStatus);
        
        List<String> violations = new ArrayList<>();
        boolean approved = "VERIFIED".equals(kycStatus);
        
        if (!approved) {
            violations.add("KYC status is not verified: " + kycStatus);
        }
        
        return ComplianceCheckResponse.builder()
                .approved(approved)
                .status(approved ? "APPROVED" : "REJECTED")
                .violations(violations)
                .warnings(new ArrayList<>())
                .reason(approved ? "KYC verification passed" : "KYC verification required")
                .build();
    }
    
    /**
     * Perform suitability check for investment recommendation
     */
    public ComplianceCheckResponse performSuitabilityCheck(String clientRiskProfile, 
                                                           String productRiskRating) {
        log.info("Performing suitability check - Client: {}, Product: {}", 
                clientRiskProfile, productRiskRating);
        
        List<String> violations = new ArrayList<>();
        boolean approved = checkRiskAlignment(clientRiskProfile, productRiskRating);
        
        if (!approved) {
            violations.add("Product risk rating does not match client risk profile");
        }
        
        return ComplianceCheckResponse.builder()
                .approved(approved)
                .status(approved ? "SUITABLE" : "UNSUITABLE")
                .violations(violations)
                .warnings(new ArrayList<>())
                .reason(approved ? "Investment is suitable for client" : "Investment not suitable for client")
                .build();
    }
    
    private boolean checkRiskAlignment(String clientRisk, String productRisk) {
        // Simplified risk alignment check
        if ("CONSERVATIVE".equals(clientRisk)) {
            return "LOW".equals(productRisk);
        } else if ("MODERATE".equals(clientRisk)) {
            return "LOW".equals(productRisk) || "MEDIUM".equals(productRisk);
        } else if ("AGGRESSIVE".equals(clientRisk)) {
            return true; // Can take any risk level
        }
        return false;
    }
}
