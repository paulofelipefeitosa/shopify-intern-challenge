package com.shopify.developerinternchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;

@SpringBootApplication(exclude = RepositoryRestMvcAutoConfiguration.class)
public class DeveloperInternChallengeApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(DeveloperInternChallengeApplication.class, args);
	}
}
