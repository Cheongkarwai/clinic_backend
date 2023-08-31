package com.cheong.clinic_api.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaginatedResponse<T>{

	private List<T> data;
	
	private String previous;
	
	private String next;
	
	public PaginatedResponse(List<T> data) {
		this.data = data;
	}
}
