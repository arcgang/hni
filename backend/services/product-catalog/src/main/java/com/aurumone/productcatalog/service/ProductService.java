package com.aurumone.productcatalog.service;

import com.aurumone.domain.enums.ProductType;
import com.aurumone.domain.enums.RiskProfile;
import com.aurumone.domain.events.ProductCreatedEvent;
import com.aurumone.domain.product.Product;
import com.aurumone.domain.valueobjects.Money;
import com.aurumone.productcatalog.dto.CreateProductRequest;
import com.aurumone.productcatalog.dto.ProductResponse;
import com.aurumone.productcatalog.dto.UpdateProductRequest;
import com.aurumone.productcatalog.exception.ProductAlreadyExistsException;
import com.aurumone.productcatalog.exception.ProductNotFoundException;
import com.aurumone.productcatalog.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Product Service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    
    private final ProductRepository productRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {
        log.info("Creating new product: {}", request.getProductCode());
        
        // Check if product code already exists
        if (productRepository.findByProductCode(request.getProductCode()).isPresent()) {
            throw new ProductAlreadyExistsException("Product with code " + request.getProductCode() + " already exists");
        }
        
        // Create product entity
        Product product = Product.builder()
                .productName(request.getProductName())
                .productCode(request.getProductCode())
                .productType(request.getProductType())
                .description(request.getDescription())
                .riskRating(request.getRiskRating())
                .minRiskProfile(request.getMinRiskProfile())
                .minInvestment(new Money(request.getMinInvestment(), request.getCurrency()))
                .issuer(request.getIssuer())
                .isActive(true)
                .build();
        
        product = productRepository.save(product);
        
        // Publish event
        ProductCreatedEvent event = ProductCreatedEvent.create(
                product.getProductId(),
                product.getProductName(),
                product.getProductCode(),
                product.getProductType(),
                product.getRiskRating(),
                product.getMinInvestment() != null ? product.getMinInvestment().getValue() : null,
                product.getMinInvestment() != null ? product.getMinInvestment().getCurrency() : null
        );
        kafkaTemplate.send("product-events", event);
        
        log.info("Product created successfully: {}", product.getProductId());
        return toResponse(product);
    }
    
    @Transactional(readOnly = true)
    public ProductResponse getProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));
        return toResponse(product);
    }
    
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByType(ProductType productType) {
        return productRepository.findActiveProductsByType(productType).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByRiskRating(Integer riskRating) {
        return productRepository.findByRiskRating(riskRating).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ProductResponse> getSuitableProducts(RiskProfile riskProfile, BigDecimal maxInvestment) {
        log.info("Finding suitable products for risk profile: {} and max investment: {}", riskProfile, maxInvestment);
        
        // Map risk profile to max risk rating
        Integer maxRiskRating = mapRiskProfileToRating(riskProfile);
        
        return productRepository.findSuitableProducts(maxRiskRating, maxInvestment).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public ProductResponse updateProduct(UUID productId, UpdateProductRequest request) {
        log.info("Updating product: {}", productId);
        
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));
        
        if (request.getProductName() != null) {
            product.setProductName(request.getProductName());
        }
        if (request.getProductType() != null) {
            product.setProductType(request.getProductType());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if (request.getRiskRating() != null) {
            product.setRiskRating(request.getRiskRating());
        }
        if (request.getMinRiskProfile() != null) {
            product.setMinRiskProfile(request.getMinRiskProfile());
        }
        if (request.getMinInvestment() != null && request.getCurrency() != null) {
            product.setMinInvestment(new Money(request.getMinInvestment(), request.getCurrency()));
        }
        if (request.getIssuer() != null) {
            product.setIssuer(request.getIssuer());
        }
        if (request.getIsActive() != null) {
            product.setIsActive(request.getIsActive());
        }
        
        product = productRepository.save(product);
        
        log.info("Product updated successfully: {}", productId);
        return toResponse(product);
    }
    
    @Transactional
    public void deactivateProduct(UUID productId) {
        log.info("Deactivating product: {}", productId);
        
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));
        
        product.setIsActive(false);
        productRepository.save(product);
        
        log.info("Product deactivated successfully: {}", productId);
    }
    
    private ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productCode(product.getProductCode())
                .productType(product.getProductType())
                .description(product.getDescription())
                .riskRating(product.getRiskRating())
                .minRiskProfile(product.getMinRiskProfile())
                .minInvestment(product.getMinInvestment() != null ? product.getMinInvestment().getValue() : null)
                .currency(product.getMinInvestment() != null ? product.getMinInvestment().getCurrency() : null)
                .issuer(product.getIssuer())
                .isActive(product.getIsActive())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
    
    private Integer mapRiskProfileToRating(RiskProfile riskProfile) {
        // Map risk profiles to maximum risk rating values
        // CONSERVATIVE: low-risk products (ratings 1-3)
        // MODERATE: medium-risk products (ratings 1-6)
        // AGGRESSIVE: all risk levels (ratings 1-10)
        final int CONSERVATIVE_MAX_RATING = 3;
        final int MODERATE_MAX_RATING = 6;
        final int AGGRESSIVE_MAX_RATING = 10;
        
        return switch (riskProfile) {
            case CONSERVATIVE -> CONSERVATIVE_MAX_RATING;
            case MODERATE -> MODERATE_MAX_RATING;
            case AGGRESSIVE -> AGGRESSIVE_MAX_RATING;
        };
    }
}
