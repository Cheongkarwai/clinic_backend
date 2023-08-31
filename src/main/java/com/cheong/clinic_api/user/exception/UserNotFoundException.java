package com.cheong.clinic_api.user.exception;

import java.util.Arrays;
import java.util.List;
import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class UserNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String message) {
		super(message);
	}
	

}
