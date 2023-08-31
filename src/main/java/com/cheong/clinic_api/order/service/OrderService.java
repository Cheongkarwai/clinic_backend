package com.cheong.clinic_api.order.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import jakarta.mail.MessagingException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheong.clinic_api.auth.domain.User;
import com.cheong.clinic_api.order.domain.ContactDetails;
import com.cheong.clinic_api.order.domain.Order;
import com.cheong.clinic_api.order.domain.PaymentStatus;
import com.cheong.clinic_api.order.dto.OrderDto;
import com.cheong.clinic_api.order.dto.OrderInput;
import com.cheong.clinic_api.order.dto.OrderPage;
import com.cheong.clinic_api.order.dto.PageInput;
import com.cheong.clinic_api.order.repository.OrderRepository;
import com.cheong.clinic_api.payment.domain.Payment;
import com.cheong.clinic_api.payment.repository.PaymentRepository;
import com.cheong.clinic_api.user.repository.UserRepository;
import com.cheong.clinic_api.utility.service.IEmailService;

@Service
@Transactional
public class OrderService implements IOrderService {

	private UserRepository userRepository;

	private OrderRepository orderRepository;

	private PaymentRepository paymentRepository;

	private IEmailService emailService;

	public OrderService(UserRepository userRepository, OrderRepository orderRepository,
			PaymentRepository paymentRepository, IEmailService emailService) {
		this.userRepository = userRepository;
		this.orderRepository = orderRepository;
		this.paymentRepository = paymentRepository;
		this.emailService = emailService;
	}

	@Override
	public Page<Order> findAll(Pageable pageable) {

		return orderRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Order findById(final Long id) {

		Order order = orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No order is found"));

		return order;
	}

	@Override
	public void save(OrderDto orderDto) {

		

//		Order order = new Order(orderDto);
//
//		// find all association of order
//		User user = userRepository.getReferenceById(orderDto.getUsername());
//		order.setUser(user);
//
//		orderRepository.save(order);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrderDto> findAllOrdersByUsername(String username) {

		return orderRepository.findAllOrderPricingsByUsername(username).stream()
				.map(order -> new OrderDto(order.getSubtotal(), order.getTax(), order.getUsername()))
				.collect(Collectors.toList());
	}

	// GraphQL service method
	@Override
	public OrderPage findAll(PageInput pageInput) {
		return new OrderPage(orderRepository.findAll(PageRequest.of(pageInput.getPage(), pageInput.getSize())));
	}

	@Override
	public Order create(OrderInput orderInput) {

		User user = userRepository.getReferenceById(orderInput.getUsername());

		Payment payment = paymentRepository.getReferenceById(orderInput.getTransactionId());

		ContactDetails contactDetails = ContactDetails.builder().emailAddress(orderInput.getContactDetails().getEmail())
				.contactNo(orderInput.getContactDetails().getContactNo())
				.addressLine1(orderInput.getContactDetails().getAddress().getAddressLine1())
				.addressLine2(orderInput.getContactDetails().getAddress().getAddressLine2())
				.city(orderInput.getContactDetails().getAddress().getCity())
				.zipcode(orderInput.getContactDetails().getAddress().getZipcode()).build();

		Order order = Order.builder().subtotal(orderInput.getSubtotal()).tax(orderInput.getTax())
				.paymentStatus(PaymentStatus.valueOf(orderInput.getPaymentStatus())).user(user).payment(payment)
				.contactDetails(contactDetails).build();

		Order savedOrder = orderRepository.save(order);
		sendInvoice(savedOrder.getContactDetails().getEmailAddress(),savedOrder);
		return savedOrder;
	}

	public void sendInvoice(String to,Order order) {

//		StringBuilder sb = new StringBuilder();
//
//		try (BufferedReader bufferedReader = new BufferedReader(
//				new FileReader(this.getClass().getClassLoader().getResource("static/test.html").getFile()))) {
//			String text = null;
//			while ((text = bufferedReader.readLine()) != null) {
//				sb.append(text);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		System.out.println(order.getSubtotal());

		try {
			emailService.sendHtmlEmail("cheongkarwai5@gmail.com", "Receipt", "<table border>"
					+ "	<thead>"
					+ "		<tr>"
					+ "			<th>Transaction ID</th>"
					+ "			<th>Datetime order placed</th>"
					+ "			<th>Total Amount (USD)</th>"
					+ "		</tr>"
					+ "	</thead>"
					+ "	<tbody>"
					+ "		<tr>"
					+ "			<td>"+order.getPayment().getTransactionId()+"</td>"
					+ "			<td>"+order.getDateCreated()+"</td>"
					+ "			<td>"+order.getSubtotal()+"</td>"
					+ "		</tr>"
					+ "	</tbody>"
					+ "	</table>");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
