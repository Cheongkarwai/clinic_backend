package com.cheong.clinic_api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.cheong.clinic_api.auth.domain.User;
import com.cheong.clinic_api.auth.input.UserInput;
import com.cheong.clinic_api.order.api.OrderController;
import com.cheong.clinic_api.user.api.UserController;
import com.cheong.clinic_api.user.dto.UserProfileDto;

@Component
public class UserProfileModelAssembler extends RepresentationModelAssemblerSupport<User, UserInput>{

	public UserProfileModelAssembler() {
		super(UserController.class, UserInput.class);
	}

	@Override
	public UserInput toModel(User user) {
		
//		UserModel userProfile = (UserModel) UserProfileDto.builder()
//											.email("cheong")
//											.fullName("Cheong")
//											.build();
//		
//		userProfile.setUsername(user.getUsername());
//		userProfile.setPassword(user.getPassword());
//		
//		userProfile.add(linkTo(methodOn(UserController.class).findByUsername(user.getUsername())).withSelfRel().withType("GET"));
//		userProfile.add(linkTo(methodOn(OrderController.class).findAllByUsername(user.getUsername())).withRel("orders"));
//		
//		return userProfile;
		return null;
	}
	
//	@Override
//	public CollectionModel<UserModel> toCollectionModel(Iterable<? extends User> users){
//		
//		CollectionModel<UserModel> collectionModel = super.toCollectionModel(users);
//		
//		//collectionModel.add(linkTo(methodOn(UserController.class).findAll(0, 0)).withSelfRel());
//		
//		return collectionModel;
//		
//	}
}
