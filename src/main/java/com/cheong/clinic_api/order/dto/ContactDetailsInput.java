package com.cheong.clinic_api.order.dto;

import jakarta.persistence.Embeddable;

import com.cheong.clinic_api.order.domain.ContactDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDetailsInput{

	private String email;
	
	private String contactNo;
	
	private AddressInput address;
}