package com.cheong.clinic_api.config;

//import java.nio.file.AccessDeniedException;
//import java.util.Map;
//
//import org.springframework.graphql.execution.ReactorContextManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.server.authorization.AuthorizationContext;
//
//import graphql.GraphQLContext;
//import graphql.language.StringValue;
//import graphql.schema.DataFetcher;
//import graphql.schema.DataFetchingEnvironment;
//import graphql.schema.GraphQLFieldDefinition;
//import graphql.schema.GraphQLFieldsContainer;
//import graphql.schema.idl.SchemaDirectiveWiring;
//import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
//
//class AuthDirective implements SchemaDirectiveWiring {
//
//	@Override
//	public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
//		StringValue targetAuthRole = (StringValue) environment.getDirective().getArgument("role").getArgumentValue()
//				.getValue();
//
//		System.out.println(targetAuthRole.getValue());
//
//		GraphQLFieldDefinition field = environment.getElement();
//		GraphQLFieldsContainer parentType = environment.getFieldsContainer();
//		//
//		// build a data fetcher that first checks authorisation roles before then
//		// calling the original data fetcher
//		//
//		DataFetcher originalDataFetcher = environment.getCodeRegistry().getDataFetcher(parentType, field);
//		DataFetcher authDataFetcher = new DataFetcher() {
//			@Override
//			public Object get(DataFetchingEnvironment dataFetchingEnvironment) throws Exception {
//				// GraphQLContext contextMap = dataFetchingEnvironment.getGraphQlContext();
//				// System.out.println(contextMap.get("authContext").toString());
//				// System.out.println(contextMap.get("Authorization").toString());
//				// AuthorizationContext authContext = (AuthorizationContext)
//				// contextMap.get("authContext");
//
//				if (SecurityContextHolder.getContext().getAuthentication().getAuthorities()
//						.contains(new SimpleGrantedAuthority(targetAuthRole.getValue()))) {
//					return originalDataFetcher.get(dataFetchingEnvironment);
//				} else {
//					throw new AccessDeniedException("User is not allowed to access this field");
//				}
////                if (authContext.hasRole(targetAuthRole)) {
////                    return originalDataFetcher.get(dataFetchingEnvironment);
////                } else {
////                    return null;
////                }
//			}
//		};
//		//
//		// now change the field definition to have the new authorising data fetcher
//		environment.getCodeRegistry().dataFetcher(parentType, field, authDataFetcher);
//		return field;
//	}
//}
