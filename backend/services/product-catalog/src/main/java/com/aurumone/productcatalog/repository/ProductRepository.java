package com.aurumone.productcatalog.repository;

import com.aurumone.domain.enums.ProductType;
import com.aurumone.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Product Repository
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    
    Optional<Product> findByProductCode(String productCode);
    
    List<Product> findByProductType(ProductType productType);
    
    List<Product> findByIsActive(Boolean isActive);
    
    List<Product> findByRiskRating(Integer riskRating);
    
    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.productType = :productType")
    List<Product> findActiveProductsByType(@Param("productType") ProductType productType);
    
    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.riskRating <= :maxRiskRating")
    List<Product> findActiveProductsByMaxRiskRating(@Param("maxRiskRating") Integer maxRiskRating);
    
    @Query("SELECT p FROM Product p WHERE p.isActive = true " +
           "AND p.riskRating <= :maxRiskRating " +
           "AND p.minInvestment.value <= :maxInvestment")
    List<Product> findSuitableProducts(
        @Param("maxRiskRating") Integer maxRiskRating,
        @Param("maxInvestment") BigDecimal maxInvestment
    );
}
