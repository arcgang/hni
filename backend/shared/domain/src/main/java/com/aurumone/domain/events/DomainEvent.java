package com.aurumone.domain.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Base Domain Event
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class DomainEvent {
    
    private UUID eventId;
    private String eventType;
    private LocalDateTime occurredAt;
    private String aggregateId;
    private Integer version;
    
    protected DomainEvent(String eventType, String aggregateId) {
        this.eventId = UUID.randomUUID();
        this.eventType = eventType;
        this.occurredAt = LocalDateTime.now();
        this.aggregateId = aggregateId;
        this.version = 1;
    }
}
