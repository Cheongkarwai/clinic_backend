package com.cheong.clinic_api.user.fetcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.cheong.clinic_api.auth.domain.User;
import com.cheong.clinic_api.user.service.IUserService;
//import com.netflix.graphql.dgs.DgsComponent;
//import com.netflix.graphql.dgs.DgsData;
//import com.netflix.graphql.dgs.DgsQuery;
//import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;

import graphql.relay.Connection;
import graphql.relay.DefaultConnection;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.DefaultPageInfo;
import graphql.relay.Edge;
import graphql.relay.PageInfo;
import graphql.relay.SimpleListConnection;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class UserDataFetcher {

	private final IUserService userService;
	public static final Logger LOGGER = LoggerFactory.getLogger(UserDataFetcher.class.getName());

	@DgsData(parentType = "Query", field = "users")
	public Connection<User> findAll(DataFetchingEnvironment dataFetchingEnvironment) {
		Integer first = dataFetchingEnvironment.getArgument("first");
		String after = dataFetchingEnvironment.getArgument("after");
		String before = dataFetchingEnvironment.getArgument("before");
		
		return userService.findAll(first, after,before);
	}

	@DgsData(parentType = "Query", field = "user")
	public User findByUsername(@InputArgument String username) {
		LOGGER.info("Received argument : {}", username);
		return userService.findByUsername(username);
	}

	@DgsData(parentType = "Mutation", field = "deleteUser")
	public String deleteByUsername(@InputArgument String username) {
		userService.deleteByUsername(username);
		return null;
	}

//	@MutationMapping("changePassword")
//	public String changePassword(@Argument String username, @Argument String password) {
//		return userService.changePasswordByUsername(username, password);
//	}
//	
//	@MutationMapping("updateProfile")
//	public UserProfile updateProfile(@Argument String username, @Argument UserProfileInput userProfile) {
//		return userService.updateProfileByUsername(username,userProfile);
//	}
}
