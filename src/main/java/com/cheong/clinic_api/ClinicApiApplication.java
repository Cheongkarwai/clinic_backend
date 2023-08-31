package com.cheong.clinic_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ClinicApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicApiApplication.class, args);
	}

}
