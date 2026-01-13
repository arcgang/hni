package com.aurumone.clientmanagement.controller;

import com.aurumone.clientmanagement.dto.ClientResponse;
import com.aurumone.clientmanagement.dto.CreateClientRequest;
import com.aurumone.clientmanagement.dto.UpdateKYCRequest;
import com.aurumone.clientmanagement.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Client Management REST Controller
 */
@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
@Tag(name = "Client Management", description = "APIs for managing HNI/UHNI client profiles")
public class ClientController {
    
    private final ClientService clientService;
    
    @PostMapping
    @Operation(summary = "Create a new client", description = "Creates a new HNI/UHNI client profile")
    public ResponseEntity<ClientResponse> createClient(@Valid @RequestBody CreateClientRequest request) {
        ClientResponse response = clientService.createClient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{clientId}")
    @Operation(summary = "Get client by ID", description = "Retrieves client details by client ID")
    public ResponseEntity<ClientResponse> getClient(@PathVariable UUID clientId) {
        ClientResponse response = clientService.getClient(clientId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(summary = "Get all clients", description = "Retrieves all client profiles")
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        List<ClientResponse> response = clientService.getAllClients();
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{clientId}/kyc")
    @Operation(summary = "Update KYC status", description = "Updates KYC verification status for a client")
    public ResponseEntity<ClientResponse> updateKYC(
            @PathVariable UUID clientId,
            @Valid @RequestBody UpdateKYCRequest request) {
        ClientResponse response = clientService.updateKYC(clientId, request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{clientId}/net-worth")
    @Operation(summary = "Get client net worth", description = "Retrieves client's net worth information")
    public ResponseEntity<ClientResponse> getNetWorth(@PathVariable UUID clientId) {
        ClientResponse response = clientService.getNetWorth(clientId);
        return ResponseEntity.ok(response);
    }
}
