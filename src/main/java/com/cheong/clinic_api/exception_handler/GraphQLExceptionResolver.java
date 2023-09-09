package com.cheong.clinic_api.exception_handler;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import com.cheong.clinic_api.user.exception.UserNotFoundException;
import com.netflix.graphql.types.errors.TypedGraphQLError;

import graphql.GraphQLError;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import reactor.core.publisher.Mono;

@Component
public class GraphQLExceptionResolver implements DataFetcherExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GraphQLExceptionResolver.class);

	@Override
	public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(DataFetcherExceptionHandlerParameters parameters) {

		if (parameters.getException() instanceof UserNotFoundException userNotFoundException) {

			GraphQLError graphQLError = TypedGraphQLError.newNotFoundBuilder().
					message(userNotFoundException.getMessage())
					.path(parameters.getPath()).build();
			
			DataFetcherExceptionHandlerResult result = DataFetcherExceptionHandlerResult.newResult()
					.error(graphQLError)
					.build();
			
			return CompletableFuture.completedFuture(result);
		}

		if(parameters.getException() instanceof BadCredentialsException badCredentialsException){

			GraphQLError graphQLError = TypedGraphQLError.newNotFoundBuilder().
					message(badCredentialsException.getMessage())
					.path(parameters.getPath()).build();

			DataFetcherExceptionHandlerResult result = DataFetcherExceptionHandlerResult.newResult()
					.error(graphQLError)
					.build();

			return CompletableFuture.completedFuture(result);
 		}

//		if (exception instanceof BadCredentialsException) {
//			BadCredentialsException badCredentialsException = (BadCredentialsException) exception;
//			return Mono.just(Collections
//					.singletonList(GraphqlErrorBuilder.newError(environment).errorType(ErrorType.UNAUTHORIZED)
//							.message(badCredentialsException.getMessage(), badCredentialsException).build()));
//		}
		
		return DataFetcherExceptionHandler.super.handleException(parameters);
	}

}
