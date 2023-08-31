package com.cheong.clinic_api.auth.graphql;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;

import com.cheong.clinic_api.auth.input.LoginInput;
import com.cheong.clinic_api.auth.input.Token;
import com.cheong.clinic_api.auth.input.TokenInput;
import com.cheong.clinic_api.auth.input.UserInput;
import com.cheong.clinic_api.auth.service.TokenService;
import com.cheong.clinic_api.user.service.IUserService;
import com.cheong.clinic_api.utility.service.IEmailService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;

import lombok.RequiredArgsConstructor;

@DgsComponent
public class AuthGraphQL {
	
	private IUserService userService;
	private TokenService tokenService;
	private IEmailService emailService;
	private AuthenticationProvider daoAuthenticationProvider;
	private AuthenticationProvider jwtAuthenticationProvider;
	
	public AuthGraphQL(IUserService userService, TokenService tokenService, IEmailService emailService,
			AuthenticationProvider daoAuthenticationProvider, AuthenticationProvider jwtAuthenticationProvider) {
		this.userService = userService;
		this.tokenService = tokenService;
		this.emailService = emailService;
		this.daoAuthenticationProvider = daoAuthenticationProvider;
		this.jwtAuthenticationProvider = jwtAuthenticationProvider;
	}
	

	@DgsData(parentType="Mutation",field="token")
	Token generateToken(@InputArgument("input") TokenInput tokenRequest) {
		
		Authentication authentication = jwtAuthenticationProvider
				.authenticate(new BearerTokenAuthenticationToken(tokenRequest.getRefreshToken()));

		String accessToken = tokenService.validateRefreshToken(authentication);

		return Token.builder().accessToken(accessToken).refreshToken(tokenRequest.getRefreshToken())
				.username(tokenRequest.getUsername()).build();
	}
	
	@DgsData(parentType="Mutation",field="loginUser")
	public Token login(@InputArgument("input") LoginInput loginRequest) {
		
		Authentication authentication = daoAuthenticationProvider.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return Token.builder()
				.accessToken(tokenService.generateAccessToken((UserDetails) authentication.getPrincipal()))
				.refreshToken(tokenService.generateRefreshToken((UserDetails) authentication.getPrincipal()))
				.username(authentication.getName()).build();
	}
	
	@DgsData(parentType="Mutation",field="registerUser")
	public String register(@InputArgument("input")UserInput userModel) {
		userService.save(userModel);
		return null;
	}
	
	@DgsData(parentType="Mutation",field="sendForgotPasswordEmail")
	public boolean sendEmail(@InputArgument String emailAddress) throws jakarta.mail.MessagingException {
		return emailService.send("Forgot Password", emailAddress,"forgot-password");
	}
	
	@DgsData(parentType = "Mutation",field = "changePassword")
	public String changePassword(@InputArgument String username, @InputArgument String password) {
		return userService.changePasswordByUsername(username, password);
	}
}
