package com.aurumone.domain.events;

import com.aurumone.domain.enums.ClientSegment;
import com.aurumone.domain.enums.RiskProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Client Created Event
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientCreatedEvent extends DomainEvent {
    
    private UUID clientId;
    private String firstName;
    private String lastName;
    private String email;
    private ClientSegment segment;
    private RiskProfile riskProfile;
    
    public ClientCreatedEvent(UUID clientId, String firstName, String lastName, 
                             String email, ClientSegment segment, RiskProfile riskProfile) {
        super("ClientCreated", clientId.toString());
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.segment = segment;
        this.riskProfile = riskProfile;
    }
}
