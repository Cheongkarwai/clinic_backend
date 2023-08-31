package com.cheong.clinic_api.payment.service;

import com.cheong.clinic_api.payment.domain.Payment;
import com.cheong.clinic_api.payment.dto.PaymentInput;

public interface IPaymentService {

	Payment createPayment(PaymentInput paymentInput);
}
