package com.aurumone.portfolio.service;

import com.aurumone.domain.events.PortfolioUpdatedEvent;
import com.aurumone.domain.portfolio.Portfolio;
import com.aurumone.portfolio.dto.PortfolioResponse;
import com.aurumone.portfolio.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PortfolioService {
    
    private final PortfolioRepository portfolioRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @Transactional(readOnly = true)
    public List<PortfolioResponse> getPortfoliosByClient(UUID clientId) {
        log.info("Getting portfolios for client: {}", clientId);
        return portfolioRepository.findByClientId(clientId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public PortfolioResponse getPortfolio(UUID portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found: " + portfolioId));
        return toResponse(portfolio);
    }
    
    private PortfolioResponse toResponse(Portfolio portfolio) {
        return PortfolioResponse.builder()
                .portfolioId(portfolio.getPortfolioId())
                .clientId(portfolio.getClientId())
                .portfolioName(portfolio.getPortfolioName())
                .totalValue(portfolio.getTotalValue() != null ? portfolio.getTotalValue().getValue() : null)
                .currency(portfolio.getTotalValue() != null ? portfolio.getTotalValue().getCurrency() : null)
                .build();
    }
}
