package com.aurumone.compliance.repository;

import com.aurumone.compliance.entity.AuditEventType;
import com.aurumone.compliance.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for AuditLog entities
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {
    
    List<AuditLog> findByEntityId(UUID entityId);
    
    List<AuditLog> findByEventType(AuditEventType eventType);
    
    List<AuditLog> findByPerformedBy(String performedBy);
    
    List<AuditLog> findByEntityTypeAndEntityId(String entityType, UUID entityId);
}
