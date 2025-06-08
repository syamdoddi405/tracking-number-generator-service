package com.tracking.number.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
@EnableMongoRepositories
public class TrackingNumberGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackingNumberGeneratorApplication.class, args);
	}

}
