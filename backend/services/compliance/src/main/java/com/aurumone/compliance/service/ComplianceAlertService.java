package com.aurumone.compliance.service;

import com.aurumone.compliance.dto.*;
import com.aurumone.compliance.entity.*;
import com.aurumone.compliance.repository.ComplianceAlertRepository;
import com.aurumone.domain.events.ComplianceAlertRaisedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for managing compliance alerts
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ComplianceAlertService {
    
    private final ComplianceAlertRepository alertRepository;
    private final AuditService auditService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @Transactional
    public ComplianceAlertResponse createAlert(CreateAlertRequest request) {
        log.info("Creating compliance alert for client: {}, type: {}", 
                request.getClientId(), request.getAlertType());
        
        ComplianceAlert alert = ComplianceAlert.builder()
                .clientId(request.getClientId())
                .alertType(request.getAlertType())
                .severity(request.getSeverity())
                .status(AlertStatus.OPEN)
                .description(request.getDescription())
                .details(request.getDetails())
                .transactionId(request.getTransactionId())
                .recommendationId(request.getRecommendationId())
                .build();
        
        alert = alertRepository.save(alert);
        
        // Create audit log
        auditService.logEvent(
                alert.getId(),
                AuditEventType.ALERT_CREATED,
                "ComplianceAlert",
                "CREATE",
                "SYSTEM",
                null,
                convertToJson(alert),
                "Alert created: " + request.getDescription(),
                null
        );
        
        // Publish event to Kafka
        publishAlertEvent(alert);
        
        return mapToResponse(alert);
    }
    
    @Transactional(readOnly = true)
    public ComplianceAlertResponse getAlert(UUID alertId) {
        log.info("Retrieving compliance alert: {}", alertId);
        ComplianceAlert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found: " + alertId));
        return mapToResponse(alert);
    }
    
    @Transactional(readOnly = true)
    public List<ComplianceAlertResponse> getAllAlerts() {
        log.info("Retrieving all compliance alerts");
        return alertRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ComplianceAlertResponse> getAlertsByClient(UUID clientId) {
        log.info("Retrieving alerts for client: {}", clientId);
        return alertRepository.findByClientId(clientId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ComplianceAlertResponse> getAlertsByStatus(AlertStatus status) {
        log.info("Retrieving alerts with status: {}", status);
        return alertRepository.findByStatus(status).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public ComplianceAlertResponse reviewAlert(UUID alertId, ReviewAlertRequest request) {
        log.info("Reviewing compliance alert: {}", alertId);
        
        ComplianceAlert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found: " + alertId));
        
        String beforeState = convertToJson(alert);
        
        alert.setStatus(request.getStatus());
        alert.setReviewedBy(request.getReviewedBy());
        alert.setReviewedAt(LocalDateTime.now());
        alert.setResolution(request.getResolution());
        
        alert = alertRepository.save(alert);
        
        // Create audit log
        auditService.logEvent(
                alert.getId(),
                AuditEventType.ALERT_REVIEWED,
                "ComplianceAlert",
                "REVIEW",
                request.getReviewedBy(),
                beforeState,
                convertToJson(alert),
                "Alert reviewed and status changed to: " + request.getStatus(),
                null
        );
        
        return mapToResponse(alert);
    }
    
    private void publishAlertEvent(ComplianceAlert alert) {
        ComplianceAlertRaisedEvent event = new ComplianceAlertRaisedEvent(
                alert.getId().toString(),
                alert.getClientId().toString(),
                alert.getAlertType().name(),
                alert.getSeverity().name(),
                alert.getDescription()
        );
        
        kafkaTemplate.send("compliance-alerts", event);
        log.info("Published ComplianceAlertRaisedEvent for alert: {}", alert.getId());
    }
    
    private ComplianceAlertResponse mapToResponse(ComplianceAlert alert) {
        return ComplianceAlertResponse.builder()
                .id(alert.getId())
                .clientId(alert.getClientId())
                .alertType(alert.getAlertType())
                .severity(alert.getSeverity())
                .status(alert.getStatus())
                .description(alert.getDescription())
                .details(alert.getDetails())
                .transactionId(alert.getTransactionId())
                .recommendationId(alert.getRecommendationId())
                .createdAt(alert.getCreatedAt())
                .reviewedAt(alert.getReviewedAt())
                .reviewedBy(alert.getReviewedBy())
                .resolution(alert.getResolution())
                .build();
    }
    
    private String convertToJson(Object obj) {
        // Simple conversion - in production use Jackson ObjectMapper
        return obj.toString();
    }
}
