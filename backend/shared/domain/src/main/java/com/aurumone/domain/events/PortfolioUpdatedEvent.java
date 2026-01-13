package com.aurumone.domain.events;

import com.aurumone.domain.valueobjects.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Portfolio Updated Event
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioUpdatedEvent extends DomainEvent {
    
    private UUID portfolioId;
    private UUID clientId;
    private Money totalValue;
    private String updateReason;
    
    public PortfolioUpdatedEvent(UUID portfolioId, UUID clientId, 
                                Money totalValue, String updateReason) {
        super("PortfolioUpdated", portfolioId.toString());
        this.portfolioId = portfolioId;
        this.clientId = clientId;
        this.totalValue = totalValue;
        this.updateReason = updateReason;
    }
}
