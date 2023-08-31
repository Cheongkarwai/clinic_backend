package com.cheong.clinic_api.payment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.cheong.clinic_api.common.constant.DatabaseConstant;
import com.cheong.clinic_api.order.domain.Order;
import com.cheong.clinic_api.order.domain.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name=DatabaseConstant.TBL_PAYMENT)
public class Payment {

	@Id
	@Column(name=DatabaseConstant.COL_TRANSACTION_ID)
	private String transactionId;
	
	private PaymentStatus paymentStatus;
	
	private String paymentMethod;
	
	@OneToOne(mappedBy = "payment")
	private Order order;
}
