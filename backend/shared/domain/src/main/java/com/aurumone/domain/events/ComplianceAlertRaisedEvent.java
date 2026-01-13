package com.aurumone.domain.events;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Compliance Alert Raised Event
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Builder
public class ComplianceAlertRaisedEvent extends DomainEvent {
    
    private UUID alertId;
    private UUID clientId;
    private String alertType;
    private String severity;
    private String description;
    private String actionRequired;
    
    public ComplianceAlertRaisedEvent(UUID alertId, UUID clientId, 
                                     String alertType, String severity,
                                     String description, String actionRequired) {
        super("ComplianceAlertRaised", alertId.toString());
        this.alertId = alertId;
        this.clientId = clientId;
        this.alertType = alertType;
        this.severity = severity;
        this.description = description;
        this.actionRequired = actionRequired;
    }
}
