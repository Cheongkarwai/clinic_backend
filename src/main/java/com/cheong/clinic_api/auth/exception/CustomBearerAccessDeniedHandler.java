package com.cheong.clinic_api.auth.exception;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.BearerTokenErrorCodes;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.cheong.clinic_api.common.dto.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CustomBearerAccessDeniedHandler implements AccessDeniedHandler {

	private String realmName;

	private ObjectMapper objectMapper;

	public CustomBearerAccessDeniedHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	/**
	 * Collect error details from the provided parameters and format according to
	 * RFC 6750, specifically {@code error}, {@code error_description},
	 * {@code error_uri}, and {@code scope}.
	 * 
	 * @param request               that resulted in an
	 *                              <code>AccessDeniedException</code>
	 * @param response              so that the user agent can be advised of the
	 *                              failure
	 * @param accessDeniedException that caused the invocation
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws JsonProcessingException, IOException {
		Map<String, String> parameters = new LinkedHashMap<>();
		if (this.realmName != null) {
			parameters.put("realm", this.realmName);
		}
		if (request.getUserPrincipal() instanceof AbstractOAuth2TokenAuthenticationToken) {
			parameters.put("error", BearerTokenErrorCodes.INSUFFICIENT_SCOPE);
			parameters.put("error_description",
					"The request requires higher privileges than provided by the access token.");
			parameters.put("error_uri", "https://tools.ietf.org/html/rfc6750#section-3.1");
		}
		String wwwAuthenticate = computeWWWAuthenticateHeaderValue(parameters);
		response.addHeader(HttpHeaders.WWW_AUTHENTICATE, wwwAuthenticate);
		response.setStatus(HttpStatus.FORBIDDEN.value());

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		ErrorResponse errorResponse = ErrorResponse.builder().statusCode(HttpStatus.FORBIDDEN.value())
				.error(accessDeniedException.getMessage()).method(request.getMethod()).path(request.getRequestURI())
				.timestamp(LocalDateTime.now()).build();

		response.getOutputStream().println(objectMapper.writeValueAsString(errorResponse));
	}

	/**
	 * Set the default realm name to use in the bearer token error response
	 * 
	 * @param realmName
	 */
	public void setRealmName(String realmName) {
		this.realmName = realmName;
	}

	private static String computeWWWAuthenticateHeaderValue(Map<String, String> parameters) {
		StringBuilder wwwAuthenticate = new StringBuilder();
		wwwAuthenticate.append("Bearer");
		if (!parameters.isEmpty()) {
			wwwAuthenticate.append(" ");
			int i = 0;
			for (Map.Entry<String, String> entry : parameters.entrySet()) {
				wwwAuthenticate.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
				if (i != parameters.size() - 1) {
					wwwAuthenticate.append(", ");
				}
				i++;
			}
		}
		return wwwAuthenticate.toString();
	}

}
