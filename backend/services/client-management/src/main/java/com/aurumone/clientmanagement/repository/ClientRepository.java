package com.aurumone.clientmanagement.repository;

import com.aurumone.domain.client.Client;
import com.aurumone.domain.enums.ClientSegment;
import com.aurumone.domain.enums.KYCStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Client Repository
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    
    Optional<Client> findByEmail(String email);
    
    List<Client> findBySegment(ClientSegment segment);
    
    List<Client> findByKycStatus(KYCStatus kycStatus);
    
    @Query("SELECT c FROM Client c WHERE c.kycStatus = 'EXPIRED' OR c.kycExpiryDate < CURRENT_TIMESTAMP")
    List<Client> findClientsWithExpiredKYC();
    
    List<Client> findByFamilyOffice(Boolean familyOffice);
}
