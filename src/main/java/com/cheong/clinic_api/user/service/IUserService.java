package com.cheong.clinic_api.user.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cheong.clinic_api.auth.domain.User;
import com.cheong.clinic_api.auth.input.UserInput;
import com.cheong.clinic_api.user.domain.UserProfile;
import com.cheong.clinic_api.user.dto.UserDto;
import com.cheong.clinic_api.user.dto.UserProfileDto;
import com.cheong.clinic_api.user.input.UserProfileInput;

import graphql.relay.Connection;

public interface IUserService {
	
//	List<UserDto> findAll(int currentPage, int pageSize);
	
	User findByUsername(String username);
	
	void save(UserInput userDto);
	
	void deleteByUsername(String username);

	Page<User> findAll(Pageable pageable);
	
	Connection<User> findAll(Integer first, String after,String before);

	User loadByUsername(String username);

	UserProfileDto findProfileByUsername(String username);

	String changePasswordByUsername(String username, String password);
	
	UserProfile updateProfileByUsername(String username,UserProfileInput userProfileInput);
}
