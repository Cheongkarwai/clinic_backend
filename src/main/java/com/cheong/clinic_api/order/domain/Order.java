package com.cheong.clinic_api.order.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.cheong.clinic_api.auth.domain.User;
import com.cheong.clinic_api.order.dto.OrderDto;
import com.cheong.clinic_api.payment.domain.Payment;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="tbl_order")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private BigDecimal subtotal;
	
	private BigDecimal tax;
	
	private PaymentStatus paymentStatus;
	
	private LocalDateTime dateCreated = LocalDateTime.now();
	
	@Embedded
	private ContactDetails contactDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id",referencedColumnName = "id")
	private User user;
	
	@OneToOne
	@JoinColumn(name="payment_id",referencedColumnName = "transaction_id" )
	private Payment payment;
	
	public Order(OrderDto orderModel) {
		this.subtotal = orderModel.getSubtotal();
		this.tax = orderModel.getTax();
	}
	
	@Builder
	public Order(BigDecimal subtotal,BigDecimal tax,PaymentStatus paymentStatus,User user,Payment payment,ContactDetails contactDetails) {
		this.subtotal = subtotal;
		this.tax = tax;
		this.paymentStatus = paymentStatus;
		this.user = user;
		this.payment = payment;
		this.contactDetails = contactDetails;
	}
	
}
