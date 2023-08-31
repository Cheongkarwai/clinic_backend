package com.cheong.clinic_api.order.assembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.cheong.clinic_api.order.api.OrderController;
import com.cheong.clinic_api.order.domain.Order;
import com.cheong.clinic_api.order.dto.OrderDto;
import com.cheong.clinic_api.user.api.UserController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderModelAssembler extends RepresentationModelAssemblerSupport<Order,OrderDto>{

	public OrderModelAssembler() {
		super(OrderController.class, OrderDto.class);
		
	}

	@Override
	public OrderDto toModel(Order order) {
		
		OrderDto orderModel = new OrderDto(order);
		
		orderModel.add(linkTo(methodOn(OrderController.class).findById(order.getId())).withSelfRel());
		
		orderModel.add(linkTo(methodOn(OrderController.class).save(null)).withRel("create"));
		
		orderModel.add(linkTo(methodOn(UserController.class).findByUsername(order.getUser().getId())).withRel("user"));
		
		orderModel.add(linkTo(methodOn(OrderController.class).findAll(null,null)).withRel("collection"));
		
		return orderModel;
	}
	
	@Override
	public CollectionModel<OrderDto> toCollectionModel(Iterable<? extends Order> orders){
		
		CollectionModel<OrderDto> collectionModel = super.toCollectionModel(orders);
		
		collectionModel.add(linkTo(methodOn(OrderController.class).findAll(null,null)).withSelfRel());
		
		return collectionModel;
	}

}
