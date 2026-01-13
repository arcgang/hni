package com.aurumone.compliance.listener;

import com.aurumone.compliance.dto.CreateAlertRequest;
import com.aurumone.compliance.entity.AlertSeverity;
import com.aurumone.compliance.entity.AlertType;
import com.aurumone.compliance.service.ComplianceAlertService;
import com.aurumone.domain.events.ClientCreatedEvent;
import com.aurumone.domain.events.RecommendationGeneratedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Kafka event listener for compliance monitoring
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ComplianceEventListener {
    
    private final ComplianceAlertService alertService;
    
    @KafkaListener(topics = "client-events", groupId = "compliance-service")
    public void handleClientCreatedEvent(ClientCreatedEvent event) {
        log.info("Received ClientCreatedEvent for client: {}", event.getClientId());
        
        // Create KYC verification alert for new clients
        CreateAlertRequest alertRequest = CreateAlertRequest.builder()
                .clientId(UUID.fromString(event.getClientId()))
                .alertType(AlertType.KYC_VERIFICATION_REQUIRED)
                .severity(AlertSeverity.HIGH)
                .description("KYC verification required for new client")
                .details("Client created: " + event.getClientName())
                .build();
        
        alertService.createAlert(alertRequest);
    }
    
    @KafkaListener(topics = "advisory-events", groupId = "compliance-service")
    public void handleRecommendationEvent(RecommendationGeneratedEvent event) {
        log.info("Received RecommendationGeneratedEvent: {}", event.getRecommendationId());
        
        // Monitor AI recommendations for compliance
        // Suitability checks would be performed here
        log.debug("Monitoring recommendation for compliance: {}", event.getRecommendationId());
    }
}
