package com.aurumone.domain.events;

import com.aurumone.domain.enums.ProductType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Product Created Event
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ProductCreatedEvent extends DomainEvent {
    
    private UUID productId;
    private String productName;
    private String productCode;
    private ProductType productType;
    private Integer riskRating;
    private BigDecimal minInvestment;
    private String currency;
    
    public static ProductCreatedEvent create(UUID productId, String productName, String productCode,
                                              ProductType productType, Integer riskRating, 
                                              BigDecimal minInvestment, String currency) {
        ProductCreatedEvent event = new ProductCreatedEvent();
        event.setEventId(UUID.randomUUID());
        event.setEventType("ProductCreated");
        event.setOccurredAt(java.time.LocalDateTime.now());
        event.setAggregateId(productId.toString());
        event.setVersion(1);
        event.productId = productId;
        event.productName = productName;
        event.productCode = productCode;
        event.productType = productType;
        event.riskRating = riskRating;
        event.minInvestment = minInvestment;
        event.currency = currency;
        return event;
    }
}
