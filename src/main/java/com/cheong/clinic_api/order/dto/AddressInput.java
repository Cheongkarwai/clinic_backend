package com.cheong.clinic_api.order.dto;

import com.cheong.clinic_api.order.domain.ContactDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressInput {

	private String addressLine1;
	
	private String addressLine2;
	
	private String city;
	
	private String zipcode;
}
