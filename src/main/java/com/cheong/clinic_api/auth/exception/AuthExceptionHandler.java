package com.cheong.clinic_api.auth.exception;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cheong.clinic_api.common.dto.ErrorResponse;

@RestControllerAdvice
public class AuthExceptionHandler {

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleException(NoSuchElementException noSuchElementException, HttpServletRequest request) {

		return ErrorResponse.builder().error(noSuchElementException.getMessage()).method(request.getMethod())
				.timestamp(LocalDateTime.now()).statusCode(HttpStatus.NOT_FOUND.value()).path(request.getRequestURI())
				.build();

	}

	@ExceptionHandler(value = RefreshTokenExpiredException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorResponse handleException(RefreshTokenExpiredException refreshTokenExpiredException,
			HttpServletRequest request) {

		return ErrorResponse.builder().error(refreshTokenExpiredException.getMessage()).method(request.getMethod())
				.timestamp(LocalDateTime.now()).statusCode(HttpStatus.UNAUTHORIZED.value())
				.path(request.getRequestURI()).build();
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorResponse handleException(InvalidBearerTokenException exception, HttpServletRequest request) {

		return ErrorResponse.builder().error(exception.getMessage()).method(request.getMethod())
				.timestamp(LocalDateTime.now()).statusCode(HttpStatus.UNAUTHORIZED.value())
				.path(request.getRequestURI()).build();

	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorResponse handleException(AuthenticationServiceException exception, HttpServletRequest request) {

		return ErrorResponse.builder().error(exception.getMessage()).method(request.getMethod())
				.timestamp(LocalDateTime.now()).statusCode(HttpStatus.UNAUTHORIZED.value())
				.path(request.getRequestURI()).build();
	}
}