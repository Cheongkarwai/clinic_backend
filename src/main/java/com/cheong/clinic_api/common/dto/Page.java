package com.cheong.clinic_api.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Page {

	private Integer first;
	
	private String after;
	
	private Integer last;
	
	private String before;
}
