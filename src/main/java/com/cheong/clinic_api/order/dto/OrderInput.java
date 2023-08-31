package com.cheong.clinic_api.order.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderInput {

	private BigDecimal tax;
	
	private BigDecimal subtotal;
	
	private String username;
	
	private String paymentStatus;
	
	private String transactionId;
	
	private ContactDetailsInput contactDetails;
}
