package com.aurumone.compliance.controller;

import com.aurumone.compliance.entity.AuditLog;
import com.aurumone.compliance.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Audit Trail
 */
@RestController
@RequestMapping("/api/v1/compliance/audit")
@RequiredArgsConstructor
@Tag(name = "Audit Trail", description = "APIs for accessing audit logs")
public class AuditController {
    
    private final AuditService auditService;
    
    @GetMapping("/entity/{entityId}")
    @Operation(summary = "Get audit trail", description = "Retrieves complete audit trail for an entity")
    public ResponseEntity<List<AuditLog>> getAuditTrail(@PathVariable UUID entityId) {
        List<AuditLog> logs = auditService.getAuditTrail(entityId);
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping("/user/{username}")
    @Operation(summary = "Get user actions", description = "Retrieves all actions performed by a user")
    public ResponseEntity<List<AuditLog>> getUserActions(@PathVariable String username) {
        List<AuditLog> logs = auditService.getAuditsByUser(username);
        return ResponseEntity.ok(logs);
    }
}
