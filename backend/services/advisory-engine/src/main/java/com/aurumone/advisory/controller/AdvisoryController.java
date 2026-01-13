package com.aurumone.advisory.controller;

import com.aurumone.advisory.dto.RecommendationRequest;
import com.aurumone.advisory.dto.RecommendationResponse;
import com.aurumone.advisory.service.AdvisoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Advisory REST Controller
 */
@RestController
@RequestMapping("/api/v1/advisory")
@RequiredArgsConstructor
@Tag(name = "AI Advisory Engine", description = "AI-powered investment advisory and recommendations")
public class AdvisoryController {
    
    private final AdvisoryService advisoryService;
    
    @PostMapping("/recommendations")
    @Operation(summary = "Generate AI recommendation", 
               description = "Generates AI-powered investment recommendations with explainability")
    public ResponseEntity<RecommendationResponse> generateRecommendation(
            @RequestBody RecommendationRequest request) {
        RecommendationResponse response = advisoryService.generateRecommendation(request);
        return ResponseEntity.ok(response);
    }
}
