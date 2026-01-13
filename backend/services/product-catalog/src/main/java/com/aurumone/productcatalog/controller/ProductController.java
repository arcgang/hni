package com.aurumone.productcatalog.controller;

import com.aurumone.domain.enums.ProductType;
import com.aurumone.domain.enums.RiskProfile;
import com.aurumone.productcatalog.dto.CreateProductRequest;
import com.aurumone.productcatalog.dto.ProductResponse;
import com.aurumone.productcatalog.dto.UpdateProductRequest;
import com.aurumone.productcatalog.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Product Catalog REST Controller
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product Catalog", description = "APIs for managing investment products")
public class ProductController {
    
    private final ProductService productService;
    
    @PostMapping
    @Operation(summary = "Create a new product", description = "Creates a new investment product in the catalog")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{productId}")
    @Operation(summary = "Get product by ID", description = "Retrieves product details by product ID")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable UUID productId) {
        ProductResponse response = productService.getProduct(productId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieves all products in the catalog")
    public ResponseEntity<List<ProductResponse>> getAllProducts(
            @RequestParam(required = false) ProductType type,
            @RequestParam(required = false) Integer riskRating) {
        
        List<ProductResponse> response;
        
        if (type != null) {
            response = productService.getProductsByType(type);
        } else if (riskRating != null) {
            response = productService.getProductsByRiskRating(riskRating);
        } else {
            response = productService.getAllProducts();
        }
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/suitable")
    @Operation(summary = "Get suitable products", 
               description = "Retrieves products suitable for a client based on risk profile and investment amount")
    public ResponseEntity<List<ProductResponse>> getSuitableProducts(
            @RequestParam RiskProfile riskProfile,
            @RequestParam BigDecimal maxInvestment) {
        List<ProductResponse> response = productService.getSuitableProducts(riskProfile, maxInvestment);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{productId}")
    @Operation(summary = "Update product", description = "Updates an existing product")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable UUID productId,
            @Valid @RequestBody UpdateProductRequest request) {
        ProductResponse response = productService.updateProduct(productId, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{productId}")
    @Operation(summary = "Deactivate product", description = "Deactivates a product (soft delete)")
    public ResponseEntity<Void> deactivateProduct(@PathVariable UUID productId) {
        productService.deactivateProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
