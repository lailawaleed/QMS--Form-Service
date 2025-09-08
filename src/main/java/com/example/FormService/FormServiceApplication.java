package com.example.FormService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.example.FormService",
		"common"
})
@EnableJpaRepositories(basePackages = {
		"com.example.FormService",
		"common.repository"
})
//@EntityScan(basePackages = {
//		"com.qualitymanagementsystem.auth_service",
//		"common.model"
//})
@EntityScan(basePackages = {
        "com.example.FormService.model",
        "com.qualitymanagementsystem.auth_service",
        "common.model"
})

public class FormServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FormServiceApplication.class, args);
	}

}
