package com.aurumone.compliance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EntityScan(basePackages = {"com.aurumone.compliance.entity", "com.aurumone.domain"})
@EnableJpaRepositories(basePackages = "com.aurumone.compliance.repository")
public class ComplianceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ComplianceApplication.class, args);
    }
}
