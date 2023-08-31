package com.cheong.clinic_api.assembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.cheong.clinic_api.auth.domain.User;
import com.cheong.clinic_api.auth.input.UserInput;
import com.cheong.clinic_api.order.api.OrderController;
import com.cheong.clinic_api.user.api.UserController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserInput>{

	public UserModelAssembler() {
		super(UserController.class, UserInput.class);
	}

	@Override
	public UserInput toModel(User user) {
		
		UserInput userModel = new UserInput(user);
		
		userModel.add(linkTo(methodOn(UserController.class).findByUsername(user.getId())).withSelfRel().withType("GET"));
		userModel.add(linkTo(methodOn(OrderController.class).findAllByUsername(user.getId())).withRel("orders"));
		
		return userModel;
	}
	
	@Override
	public CollectionModel<UserInput> toCollectionModel(Iterable<? extends User> users){
		
		CollectionModel<UserInput> collectionModel = super.toCollectionModel(users);
		
		//collectionModel.add(linkTo(methodOn(UserController.class).findAll(0, 0)).withSelfRel());
		
		return collectionModel;
		
	}
}
