package com.aurumone.clientmanagement.service;

import com.aurumone.clientmanagement.dto.ClientResponse;
import com.aurumone.clientmanagement.dto.CreateClientRequest;
import com.aurumone.clientmanagement.dto.UpdateKYCRequest;
import com.aurumone.clientmanagement.repository.ClientRepository;
import com.aurumone.domain.client.Client;
import com.aurumone.domain.enums.KYCStatus;
import com.aurumone.domain.events.ClientCreatedEvent;
import com.aurumone.domain.valueobjects.Money;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Client Service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {
    
    private final ClientRepository clientRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @Transactional
    public ClientResponse createClient(CreateClientRequest request) {
        log.info("Creating new client: {}", request.getEmail());
        
        // Create client entity
        Client client = Client.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .segment(request.getSegment())
                .riskProfile(request.getRiskProfile())
                .kycStatus(KYCStatus.PENDING)
                .netWorth(new Money(request.getNetWorthValue(), request.getCurrency()))
                .familyOffice(request.getFamilyOffice() != null ? request.getFamilyOffice() : false)
                .domicileCountry(request.getDomicileCountry())
                .build();
        
        client = clientRepository.save(client);
        
        // Publish event
        ClientCreatedEvent event = new ClientCreatedEvent(
                client.getClientId(),
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getSegment(),
                client.getRiskProfile()
        );
        kafkaTemplate.send("client-events", event);
        
        log.info("Client created successfully: {}", client.getClientId());
        return toResponse(client);
    }
    
    @Transactional(readOnly = true)
    public ClientResponse getClient(UUID clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found: " + clientId));
        return toResponse(client);
    }
    
    @Transactional(readOnly = true)
    public List<ClientResponse> getAllClients() {
        return clientRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public ClientResponse updateKYC(UUID clientId, UpdateKYCRequest request) {
        log.info("Updating KYC for client: {}", clientId);
        
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found: " + clientId));
        
        client.setKycStatus(request.getKycStatus());
        client.setKycExpiryDate(request.getKycExpiryDate());
        
        client = clientRepository.save(client);
        
        log.info("KYC updated for client: {}", clientId);
        return toResponse(client);
    }
    
    @Transactional(readOnly = true)
    public ClientResponse getNetWorth(UUID clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found: " + clientId));
        return toResponse(client);
    }
    
    private ClientResponse toResponse(Client client) {
        return ClientResponse.builder()
                .clientId(client.getClientId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .phone(client.getPhone())
                .segment(client.getSegment())
                .riskProfile(client.getRiskProfile())
                .kycStatus(client.getKycStatus())
                .netWorthValue(client.getNetWorth() != null ? client.getNetWorth().getValue() : null)
                .currency(client.getNetWorth() != null ? client.getNetWorth().getCurrency() : null)
                .familyOffice(client.getFamilyOffice())
                .domicileCountry(client.getDomicileCountry())
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .kycExpiryDate(client.getKycExpiryDate())
                .build();
    }
}
