package com.cheong.clinic_api.payment.controller;

import java.io.IOException;
import java.math.BigDecimal;

import com.cheong.clinic_api.payment.domain.Payment;
import com.cheong.clinic_api.payment.dto.PaymentInput;
import com.cheong.clinic_api.payment.service.IPaymentService;
import com.cheong.clinic_api.utility.service.BrainTreeUtility;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@DgsComponent
@RequiredArgsConstructor
public class BrainTreeController {
	
	private final IPaymentService paymentService;

	@DgsData(parentType="Mutation",field="createTransaction")
	public Mono<String> createTransaction(@InputArgument String paymentId,@InputArgument BigDecimal amount) throws IOException {
		return BrainTreeUtility.createTransaction(paymentId,amount);
	}

	@DgsData(parentType="Mutation",field="generateClientToken")
	public Mono<String> generateClientToken() throws IOException {
		return BrainTreeUtility.generateClientToken();
	}
	
	@DgsData(parentType="Mutation",field="createPayment")
	public Payment createPayment(@InputArgument PaymentInput paymentInput) {
		return paymentService.createPayment(paymentInput);
	}
}
