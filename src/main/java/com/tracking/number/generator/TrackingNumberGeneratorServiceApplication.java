package com.tracking.number.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class TrackingNumberGeneratorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackingNumberGeneratorServiceApplication.class, args);
	}

}
