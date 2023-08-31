package com.cheong.clinic_api.order.fetcher;

import com.cheong.clinic_api.order.domain.Order;
import com.cheong.clinic_api.order.dto.OrderInput;
import com.cheong.clinic_api.order.dto.PageInput;
import com.cheong.clinic_api.order.service.IOrderService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;

import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class OrderDataFetcher {

	private final IOrderService orderService;

	//@PreAuthorize(value = "@order_auth.authenticateOrderEndpoint(#root)")
	@DgsData(parentType="Query",field = "orders")
	public String findAllOrders(@InputArgument PageInput pageInput) {

//		System.out.println(pageInput.getPage());
//
//		return orderService.findAll(pageInput);
		return null;
	}
	
	@DgsData(parentType="Mutation",field="createOrder")
	public Order createOrder(@InputArgument OrderInput orderInput) {
		return orderService.create(orderInput);
	}
}
