package com.cheong.clinic_api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ConfigurationProperties(prefix="jwt")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenProperties {

	private String accessTokenSecretKey;
	
	private String refreshTokenSecretKey;
}
