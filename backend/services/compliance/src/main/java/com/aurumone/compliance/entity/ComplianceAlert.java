package com.aurumone.compliance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Compliance Alert Entity
 * Represents alerts raised for compliance violations or suspicious activities
 */
@Entity
@Table(name = "compliance_alerts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceAlert {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private UUID clientId;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AlertType alertType;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AlertSeverity severity;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AlertStatus status;
    
    @Column(nullable = false, length = 500)
    private String description;
    
    @Column(columnDefinition = "TEXT")
    private String details;
    
    private UUID transactionId;
    
    private UUID recommendationId;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime reviewedAt;
    
    private String reviewedBy;
    
    @Column(columnDefinition = "TEXT")
    private String resolution;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = AlertStatus.OPEN;
        }
    }
}
