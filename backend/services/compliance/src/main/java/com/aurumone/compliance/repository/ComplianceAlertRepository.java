package com.aurumone.compliance.repository;

import com.aurumone.compliance.entity.AlertStatus;
import com.aurumone.compliance.entity.AlertType;
import com.aurumone.compliance.entity.ComplianceAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for ComplianceAlert entities
 */
@Repository
public interface ComplianceAlertRepository extends JpaRepository<ComplianceAlert, UUID> {
    
    List<ComplianceAlert> findByClientId(UUID clientId);
    
    List<ComplianceAlert> findByStatus(AlertStatus status);
    
    List<ComplianceAlert> findByAlertType(AlertType alertType);
    
    List<ComplianceAlert> findByClientIdAndStatus(UUID clientId, AlertStatus status);
    
    long countByStatus(AlertStatus status);
}
