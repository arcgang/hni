package com.aurumone.productcatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.aurumone.domain")
@EnableJpaRepositories(basePackages = "com.aurumone.productcatalog.repository")
public class ProductCatalogApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ProductCatalogApplication.class, args);
    }
}
