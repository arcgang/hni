package com.aurumone.portfolio.controller;

import com.aurumone.portfolio.dto.PortfolioResponse;
import com.aurumone.portfolio.service.PortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/portfolios")
@RequiredArgsConstructor
@Tag(name = "Portfolio Management", description = "APIs for managing client portfolios")
public class PortfolioController {
    
    private final PortfolioService portfolioService;
    
    @GetMapping("/client/{clientId}")
    @Operation(summary = "Get portfolios by client", description = "Retrieves all portfolios for a specific client")
    public ResponseEntity<List<PortfolioResponse>> getPortfoliosByClient(@PathVariable UUID clientId) {
        List<PortfolioResponse> response = portfolioService.getPortfoliosByClient(clientId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{portfolioId}")
    @Operation(summary = "Get portfolio by ID", description = "Retrieves portfolio details by ID")
    public ResponseEntity<PortfolioResponse> getPortfolio(@PathVariable UUID portfolioId) {
        PortfolioResponse response = portfolioService.getPortfolio(portfolioId);
        return ResponseEntity.ok(response);
    }
}
