package com.cheong.clinic_api.auth.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheong.clinic_api.auth.exception.RefreshTokenExpiredException;
import com.cheong.clinic_api.auth.input.TokenInput;
import com.cheong.clinic_api.user.repository.UserRepository;

@Service
@Transactional
public class TokenService {

	private final JwtEncoder accessTokenEncoder;

	private final JwtEncoder refreshTokenEncoder;

	private final JwtDecoder refreshTokenDecoder;

	private final UserDetailsService userDetailsService;

	public TokenService(JwtEncoder accessTokenEncoder, @Qualifier("refreshTokenEncoder") JwtEncoder refreshTokenEncoder,
			@Qualifier("refreshTokenDecoder") JwtDecoder refreshTokenDecoder, UserDetailsService userDetailsService) {
		this.accessTokenEncoder = accessTokenEncoder;
		this.refreshTokenEncoder = refreshTokenEncoder;
		this.refreshTokenDecoder = refreshTokenDecoder;
		this.userDetailsService = userDetailsService;
	}

	public String generateAccessToken(UserDetails userDetails) {

		String scope = userDetails.getAuthorities().stream().map(Object::toString).collect(Collectors.joining(" "));

		Instant now = Instant.now();

		JwtClaimsSet claims = JwtClaimsSet.builder().issuedAt(now).subject(userDetails.getUsername())
				.claim("jwt_acl", Arrays.asList("ADMIN"))
				.expiresAt(now.plus(20, ChronoUnit.DAYS)).claim("scope", scope).build();

		return accessTokenEncoder
				.encode(JwtEncoderParameters
						.from(JwsHeader.with(() -> MacAlgorithm.HS256.getName()).header("typ", "JWT").build(), claims))
				.getTokenValue();
	}

	public String generateRefreshToken(UserDetails userDetails) {

		String scope = userDetails.getAuthorities().stream().map(e -> e.toString()).collect(Collectors.joining(" "));

		Instant now = Instant.now();

		JwtClaimsSet claims = JwtClaimsSet.builder().issuedAt(now).subject(userDetails.getUsername())
				.expiresAt(now.plus(6, ChronoUnit.HOURS)).claim("scope", scope).build();

		return refreshTokenEncoder
				.encode(JwtEncoderParameters
						.from(JwsHeader.with(MacAlgorithm.HS256::getName).header("typ", "JWT").build(), claims))
				.getTokenValue();
	}

	public String validateRefreshToken(@NonNull Authentication authentication) {

		if (!(authentication.getCredentials() instanceof Jwt)) {
			throw new InvalidBearerTokenException("Invalid refresh token");
		}
		Jwt jwt = (Jwt) authentication.getCredentials();

		if (jwt.getExpiresAt().isBefore(Instant.now())) {
			throw new RefreshTokenExpiredException("Refresh token is expired");
		}

		UserDetails userDetails = userDetailsService.loadUserByUsername(jwt.getSubject());

		return generateAccessToken(userDetails);
	}
}
