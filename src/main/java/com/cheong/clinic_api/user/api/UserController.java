package com.cheong.clinic_api.user.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.cheong.clinic_api.assembler.UserModelAssembler;
import com.cheong.clinic_api.assembler.UserProfileModelAssembler;
import com.cheong.clinic_api.auth.domain.User;
import com.cheong.clinic_api.auth.input.UserInput;
import com.cheong.clinic_api.order.service.IOrderService;
import com.cheong.clinic_api.user.service.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private IUserService userService;
	private UserModelAssembler userModelAssembler;
	private UserProfileModelAssembler userProfileModelAssembler;
	private PagedResourcesAssembler<User> pagedResourcesAssembler;

	public UserController(IUserService userService, IOrderService orderService, UserModelAssembler userModelAssembler,
			PagedResourcesAssembler<User> pagedResourcesAssembler,
			UserProfileModelAssembler userProfileModelAssembler) {
		this.userService = userService;
		this.userModelAssembler = userModelAssembler;
		this.pagedResourcesAssembler = pagedResourcesAssembler;
		this.userProfileModelAssembler = userProfileModelAssembler;
	}

	@GetMapping
	public HttpEntity<?> findAll(Pageable pageable) {

		Page<User> pageResult = userService.findAll(pageable);

		PagedModel<UserInput> pagedUserModel = pagedResourcesAssembler.toModel(pageResult, userModelAssembler);

		return ResponseEntity.ok(pagedUserModel);

	}

	@GetMapping(value = "/{username}")
	public HttpEntity<?> findByUsername(@PathVariable String username) {

//		User user = userService.findByUsername(username);
//
//		RepresentationModel<UserModel> userModel = userModelAssembler.toModel(user);
//
//		return ResponseEntity.ok(userModel);
		return null;
	}

	@GetMapping(value = "/{username}/profile")
	public HttpEntity<UserInput> findProfileByUsername(@PathVariable String username) {

//		User user = userService.findByUsername(username);
//
//		return ResponseEntity.ok(userProfileModelAssembler.toModel(user));
		return null;
	}

	@DeleteMapping("/{username}")
	public HttpEntity<?> deleteByUsername(@PathVariable String username) {

		userService.deleteByUsername(username);

		return ResponseEntity.noContent().build();
	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public HttpEntity<?> save(@RequestBody UserInput userDto, UriComponentsBuilder uriComponentsBuilder) {

		userService.save(userDto);

		return ResponseEntity.noContent().location(
				uriComponentsBuilder.path("/api/users/{username}").buildAndExpand(userDto.getUsername()).toUri())
				.build();

	}
}
