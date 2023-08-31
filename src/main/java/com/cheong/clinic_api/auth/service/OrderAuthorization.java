package com.cheong.clinic_api.auth.service;

import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.stereotype.Component;

@Component("order_auth")
public class OrderAuthorization {

	public boolean authenticateOrderEndpoint(MethodSecurityExpressionOperations operations) {
		
		return operations.hasAuthority("SCOPE_admin") ? true : false;
	}
}
