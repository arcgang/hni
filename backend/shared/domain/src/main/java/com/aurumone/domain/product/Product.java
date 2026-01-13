package com.aurumone.domain.product;

import com.aurumone.domain.enums.ProductType;
import com.aurumone.domain.enums.RiskProfile;
import com.aurumone.domain.valueobjects.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Product Entity - Investment products catalog
 */
@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private UUID productId;
    
    @NotNull
    @Column(name = "product_name")
    private String productName;
    
    @Column(name = "product_code", unique = true)
    private String productCode;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private ProductType productType;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "risk_rating")
    private Integer riskRating; // 1-10 scale
    
    @Enumerated(EnumType.STRING)
    @Column(name = "min_risk_profile")
    private RiskProfile minRiskProfile;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "min_investment")),
        @AttributeOverride(name = "currency", column = @Column(name = "currency"))
    })
    private Money minInvestment;
    
    @Column(name = "issuer")
    private String issuer;
    
    @Column(name = "is_active")
    private Boolean isActive;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (isActive == null) {
            isActive = true;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
