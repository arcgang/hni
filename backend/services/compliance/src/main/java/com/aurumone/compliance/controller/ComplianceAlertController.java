package com.aurumone.compliance.controller;

import com.aurumone.compliance.dto.*;
import com.aurumone.compliance.entity.AlertStatus;
import com.aurumone.compliance.service.ComplianceAlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Compliance Alerts
 */
@RestController
@RequestMapping("/api/v1/compliance/alerts")
@RequiredArgsConstructor
@Tag(name = "Compliance Alerts", description = "APIs for managing compliance alerts")
public class ComplianceAlertController {
    
    private final ComplianceAlertService alertService;
    
    @PostMapping
    @Operation(summary = "Create compliance alert", description = "Creates a new compliance alert")
    public ResponseEntity<ComplianceAlertResponse> createAlert(
            @Valid @RequestBody CreateAlertRequest request) {
        ComplianceAlertResponse response = alertService.createAlert(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{alertId}")
    @Operation(summary = "Get alert by ID", description = "Retrieves compliance alert by ID")
    public ResponseEntity<ComplianceAlertResponse> getAlert(@PathVariable UUID alertId) {
        ComplianceAlertResponse response = alertService.getAlert(alertId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(summary = "Get all alerts", description = "Retrieves all compliance alerts")
    public ResponseEntity<List<ComplianceAlertResponse>> getAllAlerts() {
        List<ComplianceAlertResponse> response = alertService.getAllAlerts();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/client/{clientId}")
    @Operation(summary = "Get alerts by client", description = "Retrieves alerts for a specific client")
    public ResponseEntity<List<ComplianceAlertResponse>> getAlertsByClient(
            @PathVariable UUID clientId) {
        List<ComplianceAlertResponse> response = alertService.getAlertsByClient(clientId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/status/{status}")
    @Operation(summary = "Get alerts by status", description = "Retrieves alerts by status")
    public ResponseEntity<List<ComplianceAlertResponse>> getAlertsByStatus(
            @PathVariable AlertStatus status) {
        List<ComplianceAlertResponse> response = alertService.getAlertsByStatus(status);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{alertId}/review")
    @Operation(summary = "Review alert", description = "Reviews and updates alert status")
    public ResponseEntity<ComplianceAlertResponse> reviewAlert(
            @PathVariable UUID alertId,
            @Valid @RequestBody ReviewAlertRequest request) {
        ComplianceAlertResponse response = alertService.reviewAlert(alertId, request);
        return ResponseEntity.ok(response);
    }
}
