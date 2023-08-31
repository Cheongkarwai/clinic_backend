package com.cheong.clinic_api.config;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.cheong.clinic_api.auth.exception.CustomBearerAccessDeniedHandler;
import com.cheong.clinic_api.auth.exception.CustomBearerAuthenticationEntry;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties({ TokenProperties.class })
public class SecurityConfig {

	private TokenProperties tokenProperties;

	private CustomBearerAuthenticationEntry customBearerAuthenticationEntry;

	private CustomBearerAccessDeniedHandler customBearerAccessDeniedHandler;

	public SecurityConfig(TokenProperties tokenProperties,
			CustomBearerAuthenticationEntry customBearerAuthenticationEntry,
			CustomBearerAccessDeniedHandler customBearerAccessDeniedHandler) {
		this.tokenProperties = tokenProperties;
		this.customBearerAuthenticationEntry = customBearerAuthenticationEntry;
		this.customBearerAccessDeniedHandler = customBearerAccessDeniedHandler;
	}
	
	//Client Registration
//	public ClientRegistration keycloakClientRegistration() {
//		return ClientRegistration.withRegistrationId("keycloak")
//				.clientId("login-app")
//				.clientSecret("9XEWtWGaCmkyR8jGHdP482F7eiHXtGx8")
//				.redirectUri("http://localhost:8081/login/oauth2/code/keycloak")
//				.userInfoUri("http://localhost:8080/realms/testing/protocol/openid-connect/userinfo")
//				.userNameAttributeName("name")
//				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//				.issuerUri("http://localhost:8080/realms/testing")
//				.authorizationUri("http://localhost:8080/realms/testing/protocol/openid-connect/auth")
//				.tokenUri("http://localhost:8080/realms/testing/protocol/openid-connect/token")
//				.jwkSetUri("http://localhost:8080/realms/testing/protocol/openid-connect/certs")
//					.build();
//	}
	
	
//	public ClientRegistration facebookClientRegistration() {
//		return CommonOAuth2Provider.FACEBOOK.getBuilder("facebook")
//				.clientId("673310377677583")
//				.clientSecret("2c9d36a589dfc01b10bfa85a9e10e635")
//				.build();
//	}
//	
//	public ClientRegistration githubClientRegistration() {
//		return CommonOAuth2Provider.GITHUB.getBuilder("github")
//					.clientId("Iv1.e2c9bf249e6eb2e2")
//					.clientSecret("a5ea382c9bd32ef16591c34ef1e7f4d091eae05b")
//					.build();
//	}
	
	
//	@Bean
//	public ClientRegistrationRepository clientRepository() {
//		return new InMemoryClientRegistrationRepository(facebookClientRegistration(),githubClientRegistration());
//	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.cors(cors->Customizer.withDefaults())
				.csrf().disable()
//				.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
//						.ignoringAntMatchers("/api/auth/**"))
//				.csrf().disable()
				.authorizeHttpRequests(req -> req.requestMatchers("/product").hasRole("USER").anyRequest().permitAll())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.oauth2ResourceServer(oauth2->oauth2.jwt(Customizer.withDefaults()))
				.exceptionHandling(exception -> exception.authenticationEntryPoint(customBearerAuthenticationEntry)
						.accessDeniedHandler(customBearerAccessDeniedHandler));
		
//		http.authorizeHttpRequests(req->req.anyRequest().authenticated())
//			.oauth2Login(oauth2->Customizer.withDefaults());

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	JwtEncoder refreshTokenEncoder() {
		SecretKeySpec secretKeySpec = new SecretKeySpec(tokenProperties.getRefreshTokenSecretKey().getBytes(),
				MacAlgorithm.HS256.getName());
		JWKSource<SecurityContext> jwks = new ImmutableSecret<>(secretKeySpec);
		return new NimbusJwtEncoder(jwks);
	}

	@Bean
	JwtDecoder refreshTokenDecoder() {
		return NimbusJwtDecoder.withSecretKey(
				new SecretKeySpec(tokenProperties.getRefreshTokenSecretKey().getBytes(), MacAlgorithm.HS256.getName()))
				.macAlgorithm(MacAlgorithm.HS256).build();
	}

	// encoder for access token
	@Bean
	@Primary
	JwtEncoder accessTokenEncoder() {
		SecretKeySpec secretKeySpec = new SecretKeySpec(tokenProperties.getAccessTokenSecretKey().getBytes(),
				MacAlgorithm.HS256.getName());
		JWKSource<SecurityContext> jwks = new ImmutableSecret<>(secretKeySpec);
		return new NimbusJwtEncoder(jwks);
	}

	@Bean
	@Primary
	JwtDecoder accessTokenDecoder() {
		return NimbusJwtDecoder.withSecretKey(
				new SecretKeySpec(tokenProperties.getAccessTokenSecretKey().getBytes(), MacAlgorithm.HS256.getName()))
				.build();
	}

	@Bean
	DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder,
			UserDetailsService userDetailsService) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}

	@Bean
	JwtAuthenticationProvider jwtAuthenticationProvider(@Qualifier("refreshTokenDecoder") JwtDecoder jwtDecoder) {
		JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider(jwtDecoder);
		// jwtAuthenticationProvider.setJwtAuthenticationConverter(jwtToUserConverter);
		return jwtAuthenticationProvider;
	}
}
