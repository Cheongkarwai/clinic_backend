package com.cheong.clinic_api.utility.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.experimental.UtilityClass;
import reactor.core.publisher.Mono;

@UtilityClass
public class BrainTreeUtility {

	private WebClient webClient;
	
	private String url = "https://payments.sandbox.braintree-api.com/graphql";

	public static Mono<String> generateClientToken() throws IOException{
		
		Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("application.properties"));
		
		String generateClientTokenMutation = "{\"query\":\"mutation($input: CreateClientTokenInput) { createClientToken(input: $input) { clientToken } }\",\"variables\":{\"input\":{\"clientToken\":{\"merchantAccountId\":\""
				+ properties.getProperty("braintree.merchant.id") + "\"}}}}";
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		
		headers.put(HttpHeaders.AUTHORIZATION,
				Arrays.asList("Basic Ymo0a2N0bTh5Mmdya3p4YjpkNGU4MmUwZmM2OWM4M2VmZTM2YzVlY2FjYTE1ZDJlOQ=="));
		headers.put("Braintree-Version", Arrays.asList(properties.getProperty("braintree.version")));
		headers.put(HttpHeaders.CONTENT_TYPE, Arrays.asList(MediaType.APPLICATION_JSON_VALUE));

		webClient = WebClient.builder().baseUrl(url)
				.defaultHeaders((header) -> header.addAll(headers)).build();

		Mono<String> clientToken = webClient.post().bodyValue(generateClientTokenMutation).retrieve()
				.bodyToMono(String.class);

		return clientToken;
	}

	public static Mono<String> createTransaction(String paymentMethodId, BigDecimal amount) throws IOException {
		
		Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("application.properties"));
		
		String createTransactionMutation = "{\"query\":\"mutation($input: ChargePaymentMethodInput!) {chargePaymentMethod(input: $input) {transaction {id status}}}\",\"variables\":{\"input\":{\"paymentMethodId\":\""+paymentMethodId+"\",\"transaction\":{\"amount\":\""+amount+"\"}}}}";
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		
		headers.put(HttpHeaders.AUTHORIZATION,
				Arrays.asList("Basic Ymo0a2N0bTh5Mmdya3p4YjpkNGU4MmUwZmM2OWM4M2VmZTM2YzVlY2FjYTE1ZDJlOQ=="));
		headers.put("Braintree-Version", Arrays.asList(properties.getProperty("braintree.version")));
		headers.put(HttpHeaders.CONTENT_TYPE, Arrays.asList(MediaType.APPLICATION_JSON_VALUE));

		webClient = WebClient.builder().baseUrl(url)
				.defaultHeaders((header) -> header.addAll(headers)).build();

		Mono<String> clientToken = webClient.post().bodyValue(createTransactionMutation).retrieve()
				.bodyToMono(String.class);

		return clientToken;

	}
}
