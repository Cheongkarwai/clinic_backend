package com.cheong.clinic_api.payment.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheong.clinic_api.payment.domain.Payment;
import com.cheong.clinic_api.payment.dto.PaymentInput;
import com.cheong.clinic_api.payment.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {

	private final PaymentRepository paymentRepository;

	@Override
	public Payment createPayment(PaymentInput paymentInput) {
		return paymentRepository.save(Payment.builder().transactionId(paymentInput.getTransactionId())
				.paymentMethod(paymentInput.getPaymentMethod()).build());
	}

}
