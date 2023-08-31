package com.cheong.clinic_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//	@Bean
//	Jackson2ObjectMapperBuilder objectMapper() {
//		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
//		builder.modules(new JavaTimeModule());
//
////        // for example: Use created_at instead of createdAt
////        builder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
//
//		// skip null fields
//		builder.serializationInclusion(JsonInclude.Include.NON_NULL);
//		builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//		return builder;
//	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("*").allowCredentials(true).exposedHeaders("*");

	}
}
