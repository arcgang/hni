package com.aurumone.compliance.service;

import com.aurumone.compliance.entity.AuditEventType;
import com.aurumone.compliance.entity.AuditLog;
import com.aurumone.compliance.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service for managing audit logs
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService {
    
    private final AuditLogRepository auditLogRepository;
    
    @Transactional
    public void logEvent(UUID entityId, AuditEventType eventType, String entityType,
                         String action, String performedBy, String beforeState,
                         String afterState, String reason, String aiDecisionTrace) {
        
        AuditLog auditLog = AuditLog.builder()
                .entityId(entityId)
                .eventType(eventType)
                .entityType(entityType)
                .action(action)
                .performedBy(performedBy)
                .beforeState(beforeState)
                .afterState(afterState)
                .reason(reason)
                .aiDecisionTrace(aiDecisionTrace)
                .build();
        
        auditLogRepository.save(auditLog);
        log.info("Audit log created for entity: {}, type: {}, action: {}", 
                entityId, eventType, action);
    }
    
    @Transactional(readOnly = true)
    public List<AuditLog> getAuditTrail(UUID entityId) {
        return auditLogRepository.findByEntityId(entityId);
    }
    
    @Transactional(readOnly = true)
    public List<AuditLog> getAuditsByEventType(AuditEventType eventType) {
        return auditLogRepository.findByEventType(eventType);
    }
    
    @Transactional(readOnly = true)
    public List<AuditLog> getAuditsByUser(String username) {
        return auditLogRepository.findByPerformedBy(username);
    }
}
