package com.example.FormService.config;

import com.example.FormService.model.Severity;
import com.example.FormService.repository.SeverityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class SeverityInitializer {

    @Bean
    public CommandLineRunner initializeSeverities(SeverityRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Severity("Low", "Low severity level", new BigDecimal("0.20")));
                repository.save(new Severity("Medium", "Medium severity level", new BigDecimal("0.50")));
                repository.save(new Severity("High", "High severity level", new BigDecimal("0.80")));
            }
        };
    }
}
