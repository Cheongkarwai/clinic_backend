package com.cheong.clinic_api.dto;

import java.math.BigDecimal;

public interface OrderPricing {

	BigDecimal getSubtotal();
	BigDecimal getTax();
	String getUsername();
	
}
