package com.cheong.clinic_api.order.dto;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.cheong.clinic_api.order.domain.Order;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonRootName(value = "order")
@Relation(collectionRelation = "orders")
public class OrderDto extends RepresentationModel<OrderDto>{
	
	private BigDecimal subtotal;
	
	private BigDecimal tax;
	
	private String username;
	
	public OrderDto(Order order) {
		this.subtotal = order.getSubtotal();
		this.tax = order.getTax();
		this.username = order.getUser().getId();
	}
	
	public OrderDto(BigDecimal subtotal, BigDecimal tax,String username) {
		this.subtotal = subtotal;
		this.tax = tax;
		this.username = username;
	}
}
