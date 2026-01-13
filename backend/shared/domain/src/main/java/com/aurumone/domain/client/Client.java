package com.aurumone.domain.client;

import com.aurumone.domain.enums.ClientSegment;
import com.aurumone.domain.enums.KYCStatus;
import com.aurumone.domain.enums.RiskProfile;
import com.aurumone.domain.valueobjects.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Client Entity - Core domain model for HNI/UHNI clients
 */
@Entity
@Table(name = "clients")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "client_id")
    private UUID clientId;
    
    @NotNull
    @Column(name = "first_name")
    private String firstName;
    
    @NotNull
    @Column(name = "last_name")
    private String lastName;
    
    @Email
    @Column(name = "email", unique = true)
    private String email;
    
    @Column(name = "phone")
    private String phone;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "segment")
    private ClientSegment segment;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "risk_profile")
    private RiskProfile riskProfile;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "kyc_status")
    private KYCStatus kycStatus;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "net_worth_value")),
        @AttributeOverride(name = "currency", column = @Column(name = "net_worth_currency"))
    })
    private Money netWorth;
    
    @Column(name = "family_office")
    private Boolean familyOffice;
    
    @Column(name = "domicile_country")
    private String domicileCountry;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "kyc_expiry_date")
    private LocalDateTime kycExpiryDate;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
