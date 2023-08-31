package com.cheong.clinic_api.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cheong.clinic_api.payment.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String>{

	
}
