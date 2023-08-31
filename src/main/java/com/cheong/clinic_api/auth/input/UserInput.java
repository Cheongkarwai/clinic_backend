package com.cheong.clinic_api.auth.input;

import java.beans.ConstructorProperties;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.cheong.clinic_api.auth.domain.User;
import com.cheong.clinic_api.user.dto.UserProfileDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "users")
public class UserInput extends RepresentationModel<UserInput>{

	private String username;
	
	private String password;
	
	public UserInput(User user) {
		this.username = user.getId();
		this.password = user.getPassword();
	}
}
