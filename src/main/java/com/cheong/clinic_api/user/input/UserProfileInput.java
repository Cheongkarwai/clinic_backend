package com.cheong.clinic_api.user.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileInput {

	private String username;
	
	private String fullName;
	
	private String email;
	
	private String addressLine1;
	
	private String addressLine2;
	
	private String city;
	
	private String zipcode;
}
