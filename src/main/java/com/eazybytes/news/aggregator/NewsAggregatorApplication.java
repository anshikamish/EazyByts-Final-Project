package com.eazybytes.news.aggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableScheduling 
public class NewsAggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsAggregatorApplication.class, args);
	}

	// ✅ This is the right way
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
