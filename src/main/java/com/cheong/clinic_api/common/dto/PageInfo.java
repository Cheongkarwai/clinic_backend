package com.cheong.clinic_api.common.dto;

import java.io.Serializable;

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
public class PageInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long totalElements;
	
	private int size;
	
	private int page;
	
	private int totalPages;
	
	private boolean hasNext;
	
	private boolean hasPrev;
}
