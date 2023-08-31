package com.cheong.clinic_api.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableCriteria {

	private Search search;
	
	private Page page;
	
	private Filter filter;
}
