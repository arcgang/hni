package com.aurumone.domain.portfolio;

import com.aurumone.domain.enums.AssetClass;
import com.aurumone.domain.valueobjects.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Portfolio Holding - Individual asset in portfolio
 */
@Entity
@Table(name = "portfolio_holdings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioHolding {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "holding_id")
    private UUID holdingId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    
    @NotNull
    @Column(name = "product_id")
    private UUID productId;
    
    @Column(name = "product_name")
    private String productName;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "asset_class")
    private AssetClass assetClass;
    
    @Column(name = "quantity")
    private BigDecimal quantity;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "market_value")),
        @AttributeOverride(name = "currency", column = @Column(name = "currency"))
    })
    private Money marketValue;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "cost_basis")),
        @AttributeOverride(name = "currency", column = @Column(name = "cost_currency"))
    })
    private Money costBasis;
    
    @Column(name = "allocation_percentage")
    private BigDecimal allocationPercentage;
}
