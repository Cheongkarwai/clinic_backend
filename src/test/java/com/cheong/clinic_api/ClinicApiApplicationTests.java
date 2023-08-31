package com.cheong.clinic_api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cheong.clinic_api.ClinicApiApplication;
import com.cheong.clinic_api.dto.PaginatedResponse;

//@SpringBootTest(classes= {ClinicApiApplication.class},webEnvironment = WebEnvironment.RANDOM_PORT)
//class ClinicApiApplicationTests {
//
//	@LocalServerPort
//	private int port;
//	
//	@Autowired
//	private TestRestTemplate testRestTemplate;
//	
//	private static final Logger LOGGER = LoggerFactory.getLogger(ClinicApiApplicationTests.class);
//	
//	@Test
//	public void testIfRestTemplateIsNull() {
//		
//		assertThat(testRestTemplate != null);
//	}
//	
//	@Test
//	public void testFindAllEmployee() {
//		
//		LOGGER.info("Testing employee");
//		
//		LOGGER.info("http://localhost:"+port+"/api/users");
//		
//		assertThat(testRestTemplate.getForObject("http://localhost:"+port+"/api/users",PaginatedResponse.class).getData().size() > 0);
//	}
//	
//	@Test
//	void contextLoads() {
//	}
//
//}
