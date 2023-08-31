package com.cheong.clinic_api.auth.exception;

public class RefreshTokenExpiredException extends RuntimeException{

	public RefreshTokenExpiredException(String msg) {
		super(msg);
	}
}
