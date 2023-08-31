package com.cheong.clinic_api.auth.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cheong.clinic_api.auth.input.LoginInput;
import com.cheong.clinic_api.auth.input.Token;
import com.cheong.clinic_api.auth.input.TokenInput;
import com.cheong.clinic_api.auth.input.UserInput;
import com.cheong.clinic_api.auth.service.TokenService;
import com.cheong.clinic_api.user.service.IUserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private IUserService userService;

	private TokenService tokenService;

	private AuthenticationProvider daoAuthenticationProvider;

	private AuthenticationProvider jwtAuthenticationProvider;

	public AuthController(IUserService userService, TokenService tokenService,
			@Qualifier("daoAuthenticationProvider") AuthenticationProvider daoAuthenticationProvider,
			@Qualifier("jwtAuthenticationProvider") AuthenticationProvider jwtAuthenticationProvider) {
		this.userService = userService;
		this.tokenService = tokenService;
		this.daoAuthenticationProvider = daoAuthenticationProvider;
		this.jwtAuthenticationProvider = jwtAuthenticationProvider;
	}

	@PostMapping("/refresh-token")
	public HttpEntity<Token> generateToken(@RequestBody TokenInput tokenRequest) {

		Authentication authentication = jwtAuthenticationProvider
				.authenticate(new BearerTokenAuthenticationToken(tokenRequest.getRefreshToken()));

		String accessToken = tokenService.validateRefreshToken(authentication);

		return ResponseEntity.ok(Token.builder().accessToken(accessToken).refreshToken(tokenRequest.getRefreshToken())
				.username(tokenRequest.getUsername()).build());
	}

	@PostMapping("/login")
	public HttpEntity<Token> login(@RequestBody LoginInput loginRequest) {

		Authentication authentication = daoAuthenticationProvider.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return ResponseEntity.ok(Token.builder()
				.accessToken(tokenService.generateAccessToken((UserDetails) authentication.getPrincipal()))
				.refreshToken(tokenService.generateRefreshToken((UserDetails) authentication.getPrincipal()))
				.username(authentication.getName()).build());

	}

	@PostMapping("/register")
	public void register(@RequestBody UserInput userModel) {

		userService.save(userModel);
	}
	
}
