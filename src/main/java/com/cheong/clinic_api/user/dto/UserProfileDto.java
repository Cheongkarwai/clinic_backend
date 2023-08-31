package com.cheong.clinic_api.user.dto;

import com.cheong.clinic_api.auth.input.UserInput;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDto  {

	private String email;
	private String fullName;


}
