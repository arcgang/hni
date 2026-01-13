package com.aurumone.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.aurumone.domain")
public class PortfolioManagementApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(PortfolioManagementApplication.class, args);
    }
}
