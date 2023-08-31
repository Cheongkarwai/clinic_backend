package com.cheong.clinic_api.order.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cheong.clinic_api.dto.OrderPricing;
import com.cheong.clinic_api.order.domain.Order;
import com.cheong.clinic_api.order.dto.OrderDto;
import com.cheong.clinic_api.order.dto.OrderInput;
import com.cheong.clinic_api.order.dto.OrderPage;
import com.cheong.clinic_api.order.dto.PageInput;


public interface IOrderService {

	void save(OrderDto orderDto);
	
	Order findById(final Long id);
	
	List<OrderDto> findAllOrdersByUsername(String username);

	Page<Order> findAll(Pageable pageable);
	
	
	//GraphQL service method
	OrderPage findAll(PageInput pageInput);

	Order create(OrderInput orderInput);
}
