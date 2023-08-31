package com.cheong.clinic_api.order.domain;

import jakarta.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class ContactDetails {

	private String emailAddress;
	
	private String contactNo;
	
	private String addressLine1;
	
	private String addressLine2;
	
	private String city;
	
	private String zipcode;
}
