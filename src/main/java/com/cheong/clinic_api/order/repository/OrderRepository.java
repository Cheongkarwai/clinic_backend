package com.cheong.clinic_api.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cheong.clinic_api.dto.OrderPricing;
import com.cheong.clinic_api.order.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

	@Query(value = "SELECT o.subtotal as subtotal,o.tax as tax,o.user.id as username FROM Order o JOIN o.user u WHERE u.id = :username")
	List<OrderPricing> findAllOrderPricingsByUsername(@Param("username")String username);
	
	@Query(value = "SELECT o FROM Order o JOIN o.user u WHERE u.id = :username")
	List<Order> findAllOrdersByUsername(@Param("username") String username);
}
