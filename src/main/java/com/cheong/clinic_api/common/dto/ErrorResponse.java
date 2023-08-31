package com.cheong.clinic_api.common.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"statusCode","method","error","timestamp","path"})
public class ErrorResponse {

	private String method;
	
	@JsonProperty("status_code")
	private int statusCode;
	
	private Object error;
	
	private LocalDateTime timestamp;
	
	private String path;
	
	
	
}
