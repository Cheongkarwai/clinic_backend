package com.cheong.clinic_api.config;

//import java.math.BigDecimal;
//import java.time.format.DateTimeParseException;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.graphql.execution.RuntimeWiringConfigurer;
//import org.springframework.lang.NonNull;
//
//import graphql.schema.Coercing;
//import graphql.schema.CoercingParseLiteralException;
//import graphql.schema.CoercingParseValueException;
//import graphql.schema.CoercingSerializeException;
//import graphql.schema.GraphQLScalarType;
//import graphql.schema.idl.RuntimeWiring;
//
//@Configuration
//public class GraphQLConfig {
//
//	@Bean
//	RuntimeWiringConfigurer runtimeWiringConfigurer() {
//		return builder -> {
//			builder.directive("auth", new AuthDirective());
//			addTypeResolver(builder);
//		};
//	}
//
//	public void addTypeResolver(RuntimeWiring.Builder builder) {
//		builder.scalar(bigDecimalScalarType());
//		builder.scalar(longScalarType());
//	}
//
//	@Bean
//	GraphQLScalarType bigDecimalScalarType() {
//		return GraphQLScalarType.newScalar().name("BigDecimal").coercing(new Coercing<BigDecimal, BigDecimal>() {
//
//			@Override
//			public BigDecimal serialize(@NonNull Object dataFetcherResult) throws CoercingSerializeException {
//				if (dataFetcherResult instanceof Number) {
//					return BigDecimal.valueOf(Double.parseDouble(dataFetcherResult.toString()));
//				}
//				throw new CoercingSerializeException("Expect a BigDecimal");
//			}
//
//			@Override
//			public @NonNull BigDecimal parseValue(@NonNull Object input) throws CoercingParseValueException {
//				try {
//					if (input instanceof Number) {
//						return BigDecimal.valueOf(Double.parseDouble(input.toString()));
//					} else {
//						throw new CoercingParseValueException("Expected a Decimal");
//					}
//				} catch (DateTimeParseException e) {
//					throw new CoercingParseValueException(String.format("Not a valid decimal: '%s'.", input), e);
//				}
//
//			}
//
//			@Override
//			public @NonNull BigDecimal parseLiteral(@NonNull Object input) throws CoercingParseLiteralException {
//				if (input instanceof Number) {
//					try {
//						return BigDecimal.valueOf(Double.parseDouble(input.toString()));
//					} catch (DateTimeParseException e) {
//						throw new CoercingParseLiteralException(e);
//					}
//				} else {
//					throw new CoercingParseLiteralException("Expected a Big Decimal.");
//				}
//
//			}
//		}).build();
//	}
//	
//	@Bean
//	GraphQLScalarType longScalarType() {
//		return GraphQLScalarType.newScalar().name("Long").description("Java 8 BigDecimal as scalar.")
//				.coercing(new Coercing<Long, Long>() {
//					@Override
//					public Long serialize(final Object dataFetcherResult) {
//						if (dataFetcherResult instanceof Number) {
//							return Long.valueOf(dataFetcherResult.toString());
//						} else {
//							throw new CoercingSerializeException("Expected a BigDecimal.");
//						}
//					}
//
//					@Override
//					public Long parseValue(final Object input) {
//						try {
//							if (input instanceof Number) {
//								return Long.valueOf(input.toString());
//							} else {
//								throw new CoercingParseValueException("Expected a Decimal");
//							}
//						} catch (DateTimeParseException e) {
//							throw new CoercingParseValueException(String.format("Not a valid decimal: '%s'.", input),
//									e);
//						}
//					}
//
//					@Override
//					public Long parseLiteral(final Object input) {
//						if (input instanceof Number) {
//							try {
//								return Long.valueOf(input.toString());
//							} catch (DateTimeParseException e) {
//								throw new CoercingParseLiteralException(e);
//							}
//						} else {
//							throw new CoercingParseLiteralException("Expected a Big Decimal.");
//						}
//					}
//				}).build();
//	}
//
//}
