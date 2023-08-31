package com.cheong.clinic_api.auth.input;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {

	private String username;
	
	@JsonProperty("access_token")
	private String accessToken;
	
	@JsonProperty("refresh_token")
	private String refreshToken;
}
