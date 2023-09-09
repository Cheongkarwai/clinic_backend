package com.cheong.clinic_api.auth.datafetcher;

import com.netflix.graphql.dgs.DgsMutation;
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

@DgsComponent
public class AuthDataFetcher {
	
	private final IUserService userService;
	private final TokenService tokenService;
	private final IEmailService emailService;
	private final AuthenticationProvider daoAuthenticationProvider;
	private final AuthenticationProvider jwtAuthenticationProvider;
	
	public AuthDataFetcher(IUserService userService, TokenService tokenService, IEmailService emailService,
						   AuthenticationProvider daoAuthenticationProvider, AuthenticationProvider jwtAuthenticationProvider) {
		this.userService = userService;
		this.tokenService = tokenService;
		this.emailService = emailService;
		this.daoAuthenticationProvider = daoAuthenticationProvider;
		this.jwtAuthenticationProvider = jwtAuthenticationProvider;
	}
	

	@DgsMutation(field = "token")
	Token generateToken(@InputArgument("input") TokenInput tokenRequest) {
		
		Authentication authentication = jwtAuthenticationProvider
				.authenticate(new BearerTokenAuthenticationToken(tokenRequest.getRefreshToken()));

		String accessToken = tokenService.validateRefreshToken(authentication);

		return Token.builder().accessToken(accessToken).refreshToken(tokenRequest.getRefreshToken())
				.username(tokenRequest.getUsername()).build();
	}
	
	@DgsMutation(field = "loginUser")
			public Token login(@InputArgument LoginInput input) {
		
		Authentication authentication = daoAuthenticationProvider.authenticate(
				new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return Token.builder()
				.accessToken(tokenService.generateAccessToken((UserDetails) authentication.getPrincipal()))
				.refreshToken(tokenService.generateRefreshToken((UserDetails) authentication.getPrincipal()))
				.username(authentication.getName()).build();
	}
	
	@DgsMutation(field = "registerUser")
			public String register(@InputArgument UserInput input) {
		userService.save(input);
		return null;
	}
	
	@DgsMutation(field = "sendForgotPasswordEmail")
			public boolean sendEmail(@InputArgument String emailAddress) throws jakarta.mail.MessagingException {
		return emailService.send("Forgot Password", emailAddress,"forgot-password");
	}
	
	@DgsData(parentType = "Mutation",field = "changePassword")
	public String changePassword(@InputArgument String username, @InputArgument String password) {
		return userService.changePasswordByUsername(username, password);
	}
}
