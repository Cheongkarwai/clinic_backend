package com.cheong.clinic_api.order.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cheong.clinic_api.order.assembler.OrderModelAssembler;
import com.cheong.clinic_api.order.domain.Order;
import com.cheong.clinic_api.order.dto.OrderDto;
import com.cheong.clinic_api.order.service.IOrderService;
import com.cheong.clinic_api.order.service.OrderService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private IOrderService orderService;

	private OrderModelAssembler orderModelAssembler;

	private PagedResourcesAssembler<Order> pagedResourcesAssembler;

	public OrderController(IOrderService orderService, OrderModelAssembler orderModelAssembler,
			PagedResourcesAssembler<Order> pagedResourcesAssembler) {
		this.orderService = orderService;
		this.orderModelAssembler = orderModelAssembler;
		this.pagedResourcesAssembler = pagedResourcesAssembler;
	}

	@PostMapping
	public HttpEntity<?> save(@RequestBody OrderDto orderDto) {

		orderService.save(orderDto);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public HttpEntity<OrderDto> findById(@PathVariable Long id) {

		Order order = orderService.findById(id);
		return ResponseEntity.ok(orderModelAssembler.toModel(order));
	}

	@PreAuthorize(value = "@order_auth.authenticateOrderEndpoint(#root)")
	@GetMapping
	public HttpEntity<CollectionModel<OrderDto>> findAll(@AuthenticationPrincipal Jwt jwt, Pageable pageable) {
		return ResponseEntity.ok(pagedResourcesAssembler.toModel(orderService.findAll(pageable), orderModelAssembler));
	}

	@GetMapping("/api/users/{username}/orders")
	public HttpEntity<?> findAllByUsername(@PathVariable String username) {

		return ResponseEntity.ok(orderService.findAllOrdersByUsername(username));
	}
	
	@PostMapping("/test/send-invoice")
	public void sendInvoice() {
		orderService.save(null);
	}
}
